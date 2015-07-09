package com.boundary.meter.client.rpc;

import com.boundary.meter.client.BoundaryMeterClient;
import com.boundary.meter.client.command.*;
import com.boundary.meter.client.model.Event;
import com.boundary.meter.client.model.Measure;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

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
        this.rpc = rpcFactory.get();
        rpc.connect();
        rpc.awaitConnected(1, TimeUnit.SECONDS);
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
    public CompletableFuture<GetProcessInfoResponse> getProcessInfo(String expression, GetProcessInfo.Type type) {
        return send(GetProcessInfo.of(expression, type));
    }

    @Override
    public CompletableFuture<GetProcessInfoResponse> getProcessInfo(String expression, GetProcessInfo.Type type,
                                                           Optional<String> expression2, Optional<GetProcessInfo.Type> type2,
                                                           Optional<String> expression3, Optional<GetProcessInfo.Type> type3) {
        return send(GetProcessInfo.of(expression, type, expression2, type2, expression3, type3));
    }

    @Override
    public CompletableFuture<GetProcessTopKResponse> getProcessTopK(int number, GetProcessTopK.Type type) {
        return send(GetProcessTopK.of(number, type));
    }

    @Override
    public CompletableFuture<GetProcessTopKResponse> getProcessTopK(int number, GetProcessTopK.Type type,
                                                                    int number2, GetProcessTopK.Type type2) {
        return send(GetProcessTopK.of(number, type, number2, type2));
    }

    @Override
    public CompletableFuture<QueryMetricResponse> queryMetric(String metric, boolean Exact) {
        return send(QueryMetric.of(metric, Exact));
    }

    @Override
    public CompletableFuture<GetServiceListenersResponse> getServiceListeners() {
        return send(GetServiceListeners.of());
    }

    private <T extends Response> CompletableFuture<T> send(Command<T> command) {

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

    private void tryReconnect() {
        if (connectionPending.compareAndSet(false, true)) {
            executor.submit(() -> {
                    rpc = rpcFactory.get();
                    try {
                        rpc.connect();
                        rpc.awaitConnected(1, TimeUnit.SECONDS);
                    } catch (Exception e) {
                        LOGGER.error("Exception reconnecting", e);
                    }
                    connectionPending.set(false);

            });
        }

    }


    @Override
    public void close() throws InterruptedException {
        if (shutDown.compareAndSet(false, true)) {
            rpc.close();
        }
    }


}
