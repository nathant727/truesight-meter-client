package com.boundary.meter.client.rpc;

import com.boundary.meter.client.command.GetProcessInfo;
import com.boundary.meter.client.command.GetProcessTopK;
import com.boundary.meter.client.command.ImmutableTypedExpression;
import com.boundary.meter.client.command.ImmutableTypedNumber;
import com.boundary.meter.client.model.Event;
import com.boundary.meter.client.model.ImmutableEvent;
import com.boundary.meter.client.model.ImmutableMeasure;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Uninterruptibles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class MeterRpcHandlerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeterRpcHandlerTest.class);

    public static void main(String[] args) throws Exception {


        BoundaryRpcClientConfig config = new BoundaryRpcClientConfig();
        config.setLoggingEnabled(true);

        try (BoundaryRpcClient client = new BoundaryRpcClient(config)) {


            client.connect();
            List<CompletableFuture<?>> futures = Lists.newArrayList();

            Event e = ImmutableEvent.builder()
                    .title("rpcHandler test starting NOW")
                    .message("rpcHandler test is starting")
                    .build();

            postFuture( client.addEvent(e), futures);

            postFuture(client.discovery(), futures);

            postFuture(client.systemInformation(), futures);
            postFuture(client.getServiceListeners(), futures);
            postFuture(client.debug("all", 1), futures);
            postFuture(client.getProcessInfo(ImmutableTypedExpression.builder()
                    .type(GetProcessInfo.TypedExpression.Type.process)
                    .expression(".*meter")
                    .build()), futures);
            postFuture(client.debug("all", 0),futures );

            postFuture(client.getProcessInfo(ImmutableTypedExpression.builder()
                            .expression("kafka")
                            .type(GetProcessInfo.TypedExpression.Type.args_expr)
                            .build(),
                    ImmutableTypedExpression.builder()
                            .expression("java")
                            .type(GetProcessInfo.TypedExpression.Type.process)
                            .build()),futures );

            postFuture(client.getProcessTopK(ImmutableTypedNumber.builder()
                    .number(3)
                    .type(GetProcessTopK.TypedNumber.Type.cpu)
                    .build()), futures);

            postFuture(client.getProcessTopK(ImmutableTypedNumber.builder()
                    .number(2)
                    .type(GetProcessTopK.TypedNumber.Type.mem)
                    .build()), futures);

            postFuture(client.getProcessTopK(ImmutableTypedNumber.builder()
                            .number(5)
                            .type(GetProcessTopK.TypedNumber.Type.cpu)
                            .build(),
                    ImmutableTypedNumber.builder()
                            .number(6).
                            type(GetProcessTopK.TypedNumber.Type.mem)
                            .build()), futures);






            postFuture(client.queryMetric("foo.bar", true), futures);
            postFuture(client.queryMetric("bar", false), futures);




            Event e2 = ImmutableEvent.builder()
                    .title("arrayed - event 0")
                    .type(Event.Type.warn)
                    .build();

            Event e3 = ImmutableEvent.builder()
                    .title("arrayed - event 1")
                    .message("with a message")
                    .build();

            client.addEvents(ImmutableList.of(e2, e3));

            double val = 0.5;
            client.addMeasures(
                    ImmutableList.of(
                            ImmutableMeasure.builder()
                                    .name("foo.bar")
                                    .value(Math.sin(val))
                                    .build()
                            , ImmutableMeasure.builder()
                                    .name("bar.foo")
                                    .source("source2")
                                    .value(-Math.sin(val))
                                    .build()
                    )
            );
            client.addMeasure(ImmutableMeasure.builder()
                    .name("bar.baz")
                    .value(val)
                    .source("source3")
                    .build());


            futures.forEach(future -> {

                try {
                    future.get(1, TimeUnit.SECONDS);
                } catch (Exception e1) {

                    LOGGER.error("Exception waiting for response: ", e1);
                }

            });


        } catch (Exception e) {
            LOGGER.error("ex", e);
            System.exit(1);

        }
        System.exit(0);

    }

    private static void postFuture(CompletableFuture<?> future, List<CompletableFuture<?>> futures) {
        futures.add(future.thenAccept(o -> LOGGER.info("completed: {}", o.toString())));
    }

}