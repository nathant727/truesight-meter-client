package com.boundary.meter.client.rpc;

import com.boundary.meter.client.BoundaryMeterClient;
import com.boundary.meter.client.command.*;
import com.boundary.meter.client.model.Event;
import com.boundary.meter.client.model.Measure;
import com.boundary.meter.client.response.*;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkState;

/**
 * Exposes the {@link com.boundary.meter.client.BoundaryMeterClient} api to clients
 * and manages creation/reconnection of underlying {@link BoundaryNettyRpc}
 */
public class BoundaryRpcClient implements BoundaryMeterClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoundaryRpcClient.class);

    private final Supplier<BoundaryNettyRpc> rpcFactory;
    private final AtomicBoolean connectionPending = new AtomicBoolean(false);
    private final AtomicBoolean shutDown = new AtomicBoolean(false);
    private final ExecutorService executor;
    private volatile BoundaryNettyRpc rpc;


    public BoundaryRpcClient(BoundaryRpcClientConfig config) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        this.rpcFactory = () -> new BoundaryNettyRpc(config, workerGroup);
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public CompletableFuture<VoidResponse> addMeasures(List<Measure> measures) {
        return send(AddMeasures.of(measures));
    }

    @Override
    public CompletableFuture<VoidResponse> addMeasure(Measure measure) {
        return send(AddMeasures.of(measure));
    }

    @Override
    public CompletableFuture<VoidResponse> addEvents(List<Event> events) {
        return send(AddEvents.of(events));
    }

    @Override
    public CompletableFuture<VoidResponse> addEvent(Event event) {
        return send(AddEvents.of(event));
    }

    @Override
    public CompletableFuture<DiscoveryResponse> discovery() {
        return send(Discovery.of());
    }

    @Override
    public CompletableFuture<GetSystemInfoResponse> systemInformation() {
        return send(GetSystemInfo.of());
    }


    @Override
    public CompletableFuture<DebugResponse> debug(String section, int level) {
        return send(Debug.of(section, level));
    }

    @Override
    public CompletableFuture<GetProcessInfoResponse> getProcessInfo(GetProcessInfo.TypedExpression expression, GetProcessInfo.TypedExpression ... optional) {
        return send(GetProcessInfo.of(expression, optional));
    }

    @Override
    public CompletableFuture<GetProcessTopKResponse> getProcessTopK(GetProcessTopK.TypedNumber number, GetProcessTopK.TypedNumber ... optional) {
        return send(GetProcessTopK.of(number, optional));
    }

    @Override
    public CompletableFuture<QueryMetricResponse> queryMetric(String metric, boolean exact) {
        return send(QueryMetric.of(metric, exact));
    }

    @Override
    public CompletableFuture<GetServiceListenersResponse> getServiceListeners() {
        return send(GetServiceListeners.of());
    }

    protected  <T extends Response> CompletableFuture<T> send(Command<T> command) {

        try {
            if (!connected()) {
                throw new DisconnectedException("Not connected", command);
            }
            return rpc.sendCommand(command);
        } catch (Exception e) {
            tryReconnect();
            CompletableFuture<T> exFuture = new CompletableFuture<>();
            exFuture.completeExceptionally(e);
            return exFuture;
        }
    }

    private boolean connected() {
        return !connectionPending.get()
                && rpc != null
                && rpc.isConnected();
    }

    /**
     * public connect which should be called by clients
     * following instantiation
     * @return true if connected, false otherwise
     * @throws Exception
     */
    public boolean connect() throws Exception {
        if (connectionPending.compareAndSet(false, true)) {
            try {
                return connectSync();
            } finally {
                connectionPending.set(false);
            }
        }
        return false;
    }

    private void tryReconnect() {
        if (connectionPending.compareAndSet(false, true)) {
            executor.submit(() -> {
                try {
                    this.connectSync();
                } catch (Exception e) {
                    LOGGER.error("Exception reconnecting");
                } finally {
                    connectionPending.set(false);
                }

            });
        }
    }

    private boolean connectSync() throws Exception {
        checkState(connectionPending.get(), "Connection attempted but not marked as pending");
        rpc = rpcFactory.get();
        try {
            rpc.connect();
            return rpc.awaitConnected(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.interrupted();
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void close() throws InterruptedException {
        if (shutDown.compareAndSet(false, true)) {
            rpc.close();
        }
    }


}
