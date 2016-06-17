package com.bmc.truesight.saas.meter.client.rpc;


import com.bmc.truesight.saas.meter.client.command.Command;
import com.bmc.truesight.saas.meter.client.response.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Throwables;
import com.google.common.net.HostAndPort;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.requireNonNull;

public class TruesightMeterNettyRpc implements AutoCloseable {

    private final MeterRpcHandler handler;
    private final EventLoopGroup pool;

    private enum State {
        latent, connecting, connected, closed
    }

    private final Bootstrap bootstrap;

    private volatile ChannelFuture channelFuture;
    private final AtomicReference<State> currentState = new AtomicReference<>(State.latent);

    private static final Logger LOGGER = LoggerFactory.getLogger(TruesightMeterNettyRpc.class);

    private final HostAndPort meter;

    private final CountDownLatch connectedLatch = new CountDownLatch(1);


    public TruesightMeterNettyRpc(TruesightMeterRpcClientConfig config, EventLoopGroup workerGroup) {
        this.meter = requireNonNull(config.getMeter());
        this.handler =  new MeterRpcHandler(meter);
        pool = new NioEventLoopGroup();
        this.bootstrap = createBootstrap(config, handler, workerGroup);
    }

    private static Bootstrap createBootstrap(TruesightMeterRpcClientConfig config, MeterRpcHandler handler, EventLoopGroup workerGroup) {

        return new Bootstrap()
                .group(requireNonNull(workerGroup))
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        if (config.isLoggingEnabled()) {
                            ch.pipeline().addLast(new LoggingHandler(config.getLogLevel()));

                        }
                        ch.pipeline().addLast("jsonDecoder", new JsonObjectDecoder());
                        ch.pipeline().addLast(handler);
                    }
                })
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.TCP_NODELAY, true);
    }


    public synchronized void connect() throws Exception {
        if (!currentState.compareAndSet(State.latent, State.connecting)) {
            throw new IllegalStateException(meter.toString() + ": Cannot connect from state " + currentState.get().name());
        }
        channelFuture = bootstrap.connect(meter.getHostText(), meter.getPort());
        LOGGER.info("Connecting to: {}", meter);

        try {
            channelFuture.syncUninterruptibly();
            currentState.set(State.connected);
            connectedLatch.countDown();
        } catch (Exception e) {
            close();
            throw e;
        }

        channelFuture.channel().closeFuture().addListener(channelFuture1 -> {
            LOGGER.info("{}: Channel closed", meter);
            close();
        });
    }


    public boolean awaitConnected(long qty, TimeUnit unit) throws InterruptedException {
        switch (currentState.get()) {
            case closed:
            case latent:
                throw new IllegalStateException("Cannot awaitConnected from state " + currentState.get().name());
            case connecting:
                return connectedLatch.await(qty, unit);
            case connected:
                return true;
        }
        return false;
    }

    public <T extends Response> CompletableFuture<T> sendCommand(Command<T> command) {
        if (isClosed()) {
            throw new IllegalStateException(meter.toString() + ": Client closed");
        }
        if (!isConnected()) {
            throw new IllegalStateException(meter.toString() + ": Not connected");
        }

        try {
            return this.handler.sendCommand(command);
        } catch (DisconnectedException disconnected) {
            LOGGER.error("{}: Handler was disconnected, closing client", meter, disconnected);
            try {
                close();
            } catch (InterruptedException e) {
                LOGGER.warn("{}: interrupted during close", meter, e);
                Thread.currentThread().interrupt();
            }
            throw new IllegalStateException(meter.toString() + ": Client closed");
        } catch (JsonProcessingException e) {
            throw Throwables.propagate(e);
        }
    }

    public boolean isClosed() {
        return currentState.get().equals(State.closed);
    }

    public boolean isConnected() {
        return currentState.get().equals(State.connected);
    }

    public boolean isConnecting() {
        return currentState.get().equals(State.connecting);
    }

    @Override
    public void close() throws InterruptedException {
        if (!isClosed()) {
            synchronized (this) {
                if (isConnected()) {
                    channelFuture.channel().close();
                }
                pool.shutdownGracefully();
                currentState.set(State.closed);

            }
        }
    }
}
