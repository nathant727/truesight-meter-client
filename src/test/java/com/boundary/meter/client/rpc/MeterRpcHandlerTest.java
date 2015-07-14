package com.boundary.meter.client.rpc;

import com.boundary.meter.client.command.*;
import com.boundary.meter.client.model.Event;
import com.boundary.meter.client.model.ImmutableEvent;
import com.boundary.meter.client.model.ImmutableMeasure;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.DoubleSupplier;

public class MeterRpcHandlerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeterRpcHandlerTest.class);

    public static void main(String[] args) throws Exception {

        BoundaryRpcClientConfig config = new BoundaryRpcClientConfig();
        config.setLoggingEnabled(true);
        BoundaryRpcClient client = new BoundaryRpcClient(config);
        List<CompletableFuture<?>> futures = Lists.newArrayList();

        Event e = ImmutableEvent.builder()
                .title("rpcHandler test starting (no message)")
                .message("rpcHandler test is starting")
                .build();

        client.addEvent(e);


        futures.add(client.discovery());
        futures.add(client.systemInformation());
        futures.add(client.getServiceListeners());
        futures.add(client.debug("all", 1));
        futures.add(client.getProcessInfo(ImmutableTypedExpression.builder()
                .type(GetProcessInfo.TypedExpression.Type.process)
                .expression("meter")
                .build()));
        futures.add(client.debug("all", 0));

        futures.add(client.getProcessInfo(ImmutableTypedExpression.builder()
                        .expression("kafka")
                        .type(GetProcessInfo.TypedExpression.Type.args_expr)
                        .build(),
                ImmutableTypedExpression.builder()
                        .expression("java")
                        .type(GetProcessInfo.TypedExpression.Type.process)
                        .build()));

        futures.add(client.getProcessTopK(ImmutableTypedNumber.builder()
                .number(3)
                .type(GetProcessTopK.TypedNumber.Type.cpu)
                .build()));

        futures.add(client.getProcessTopK(ImmutableTypedNumber.builder()
                .number(2)
                .type(GetProcessTopK.TypedNumber.Type.mem)
                .build()));

        futures.add(client.getProcessTopK(ImmutableTypedNumber.builder()
                        .number(5)
                        .type(GetProcessTopK.TypedNumber.Type.cpu)
                        .build(),
                ImmutableTypedNumber.builder()
                        .number(6).
                        type(GetProcessTopK.TypedNumber.Type.mem)
                        .build()));

        DoubleSupplier ds = new DoubleSupplier() {
            double current = 0;
            @Override
            public double getAsDouble() {
                return current+=.05;
            }
        };

        Event e2 =  ImmutableEvent.builder()
                .title("arrayed - event 0")
                .type(Event.Type.warn)
                .build();

        Event e3 =  ImmutableEvent.builder()
                .title("arrayed - event 1")
                .message("with a message")
                .build();

        Event e4 =  ImmutableEvent.builder()
                .title("arrayed - event 2")
                .message("with a message")
                .type(Event.Type.critical)
                .addTags("tag1", "tag2", "tag3")
                .build();

        client.addEvents(ImmutableList.of(e2,e3));

        client.queryMetric("foo.bar", true);
        client.queryMetric("bar", false);


        CountDownLatch done = new CountDownLatch(5000);
        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(() -> {
                    double val = ds.getAsDouble();
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
                    done.countDown();
                }, 0, 1, TimeUnit.SECONDS);

        for (CompletableFuture<?> future : futures) {
            LOGGER.info(future.get().toString());
        }

        done.await();
            client.close();


    }

}