package com.boundary.meter.client.rpc;

import com.boundary.meter.client.Json;
import com.boundary.meter.client.command.Command;
import com.boundary.meter.client.command.Identified;
import com.boundary.meter.client.response.ImmutableVoidResponse;
import com.boundary.meter.client.response.Response;
import com.boundary.meter.client.response.VoidResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HostAndPort;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteOrder;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.requireNonNull;

public class MeterRpcHandler extends ChannelInboundHandlerAdapter {


    private static final Logger LOGGER = LoggerFactory.getLogger(MeterRpcHandler.class);

    private final AtomicReference<ChannelHandlerContext> ctxRef = new AtomicReference<>();
    private final AtomicInteger idRef = new AtomicInteger(0);
    private final ConcurrentMap<Integer, CommandAndFuture> pendingRequestsById = new ConcurrentHashMap<>();

    private final HostAndPort meter;
    private final ObjectMapper mapper;


    public MeterRpcHandler(HostAndPort meter) {
        this.meter = requireNonNull(meter);
        this.mapper = Json.MAPPER;
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("{}: added {}", meter, ctx);
        ctxRef.set(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("{}: removed {}", meter, ctx);
        ctxRef.set(null);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("{}: exceptionCaught: {}", meter, ctx, cause);
        ctx.fireExceptionCaught(cause);
        ctx.close();
    }

    @SuppressWarnings("unchecked")
    public <T extends Response> CompletableFuture<T> sendCommand(Command<T> command) throws DisconnectedException, JsonProcessingException {
        final ChannelHandlerContext ctx = ctxRef.get();
        if (ctx == null) {
            throw new DisconnectedException("nullContext",  command);
        }

        final int id = this.idRef.getAndIncrement();
        final Identified<T> identified = new Identified<>(command, id);
        LOGGER.debug("sendCommand: {}", identified);
        final CompletableFuture<T> future = new CompletableFuture<T>();

        CommandAndFuture<T> caf = new CommandAndFuture<>(identified, future);
        pendingRequestsById.put(id, caf);

        writeToChannel(ctx.channel(), identified).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (!channelFuture.isSuccess()) {
                    LOGGER.debug("Request id {} operation completed unsuccessfully {}", id, channelFuture.cause());
                    pendingRequestsById.remove(id);
                    future.completeExceptionally(channelFuture.cause());
                }

                if (command.getResponseType() == VoidResponse.class) {
                    pendingRequestsById.remove(id);
                    caf.future.complete((T) ImmutableVoidResponse.of());
                }
            }
        });
        return future;
    }


    private ChannelFuture writeToChannel(Channel channel, Identified identified) throws JsonProcessingException {
        ByteBuf buf = Unpooled.wrappedBuffer(mapper.writeValueAsBytes(identified)).order(ByteOrder.LITTLE_ENDIAN);
        return channel.writeAndFlush(buf);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        LOGGER.debug("Message received: {}/{}", msg.getClass(), msg.toString());

        if (msg instanceof ByteBuf) {
            final ByteBuf buf = ((ByteBuf) msg);

            try {
                // todo: look into a streaming data parser here if needed for speed
                // todo: handle rpc error codes
                JsonNode tree = mapper.readTree( new ByteBufInputStream(buf) );
                JsonNode idField = tree.get("id");
                if (idField != null) {
                    int id = idField.asInt();
                    final CommandAndFuture caf = pendingRequestsById.remove(id);
                    if (caf != null) {
                        try {
                            Command c = caf.identified.getCommand();
                            Response response = mapper.readerFor(c.getResponseType()).readValue(tree.get("result"));

                            if (buf.isReadable()) {
                                LOGGER.error("{}: Failed to read complete message: {}", meter, ByteBufUtil.hexDump(buf));
                            }
                            caf.future.complete(response);
                        } catch(Exception e) {
                            caf.future.completeExceptionally(e);
                        }
                    }
                }

            } finally {
                ReferenceCountUtil.release(buf);
            }

        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private class CommandAndFuture<T extends Response> {

        private final Identified<T> identified;
        private final CompletableFuture<T> future;

        public CommandAndFuture(Identified<T> identified, CompletableFuture<T> future) {
            this.identified = identified;
            this.future = future;
        }
    }
}
