package com.boundary.meter.client.rpc;

import com.boundary.meter.client.BoundaryMeterClient;
import com.boundary.meter.client.command.AddMeasures;
import com.boundary.meter.client.command.Command;
import com.boundary.meter.client.command.Discovery;
import com.boundary.meter.client.command.DiscoveryResponse;
import com.boundary.meter.client.command.GetServiceListeners;
import com.boundary.meter.client.command.GetServiceListenersResponse;
import com.boundary.meter.client.command.Response;
import com.boundary.meter.client.command.VoidResponse;
import com.boundary.meter.client.model.Measure;
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
        return send(new AddMeasures(measures));
    }

    @Override
    public CompletableFuture<DiscoveryResponse> discovery() {
        return send(Discovery.INSTANCE);
    }

    @Override
    public CompletableFuture<GetServiceListenersResponse> getServiceListeners() {
        return send(GetServiceListeners.INSTANCE);
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
