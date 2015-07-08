package com.boundary.meter.client.model;

import org.immutables.value.Value;

import java.time.Instant;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Immutable representation of a single measurement
 */
@Value.Immutable
public abstract class Measure {


    public abstract String name();
    public abstract double value();
    @Value.Default
    public Instant timestamp() {
        return Instant.now();
    }
    public abstract Optional<String> source();

    @Value.Check
    protected void check() {
        checkArgument(name().getBytes().length <= 100, "Metric name length must not be > 100 bytes");
        source().ifPresent(m -> {
            checkArgument(m.getBytes().length <= 100, "Source name length must not be > 100 bytes");
        });
    }
}
