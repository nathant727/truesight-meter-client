package com.boundary.meter.client.rpc;

import com.boundary.meter.client.model.Measure;
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
import java.util.stream.DoubleStream;

public class MeterRpcHandlerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeterRpcHandlerTest.class);

    public static void main(String[] args) throws Exception {

        BoundaryRpcClientConfig config = new BoundaryRpcClientConfig();
        config.setLoggingEnabled(true);
        BoundaryRpcClient client = new BoundaryRpcClient(config);
        List<CompletableFuture<?>> futures = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            futures.add(client.discovery());
        }

        for (int i = 0; i < 10; i++) {
            futures.add(client.getServiceListeners());
        }

        DoubleStream.iterate(0, (a) -> a + .1);

        DoubleSupplier ds = new DoubleSupplier() {
            double current = 0;
            @Override
            public double getAsDouble() {
                return current+=.05;
            }
        };
        CountDownLatch done = new CountDownLatch(5000);
        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(() -> {
                    double val =  ds.getAsDouble();
                    client.addMeasures(
                            ImmutableList.of(
                                    new Measure("foo.bar", Math.sin(val))
                                    ,new Measure("bar.foo", -Math.sin(val))
                                    ,new Measure("bar.baz",  val)
                            )
                    );
                    done.countDown();
                }, 0, 1, TimeUnit.SECONDS);


        for (CompletableFuture<?> future : futures) {
            LOGGER.info(future.get().toString());
        }

        done.await();
            client.close();


    }

}