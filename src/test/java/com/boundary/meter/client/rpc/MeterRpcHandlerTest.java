package com.boundary.meter.client.rpc;

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

        for (int i = 0; i < 10; i++) {
            futures.add(client.discovery());
        }

        for (int i = 0; i < 10; i++) {
            futures.add(client.getServiceListeners());
        }

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

        client.addEvents(ImmutableList.of(e2,e3));

        CountDownLatch done = new CountDownLatch(5000);
        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(() -> {
                    double val =  ds.getAsDouble();
                    client.addMeasures(
                            ImmutableList.of(
                                    ImmutableMeasure.of("foo.bar", Math.sin(val))
                                    ,ImmutableMeasure.of("bar.foo", -Math.sin(val))
                            )
                    );
                    client.addMeasure(ImmutableMeasure.of("bar.baz",  val));
                    done.countDown();
                }, 0, 1, TimeUnit.SECONDS);


        for (CompletableFuture<?> future : futures) {
            LOGGER.info(future.get().toString());
        }

        done.await();
            client.close();


    }

}