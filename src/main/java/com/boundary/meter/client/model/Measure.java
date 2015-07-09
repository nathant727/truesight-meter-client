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

    public static Measure of(String name, double value) {
        return ImmutableMeasure.builder()
                .name(name)
                .value(value)
                .build();
    }

    public static Measure of(String name, double value, Instant timestamp) {
        return ImmutableMeasure.builder()
                .name(name)
                .value(value)
                .timestamp(timestamp)
                .build();
    }

    public static Measure of(String name, double value, String source) {
        return ImmutableMeasure.builder()
                .name(name)
                .value(value)
                .source(source)
                .build();
    }

    public static Measure of(String name, double value, Instant timestamp, String source) {
        return ImmutableMeasure.builder()
                .name(name)
                .value(value)
                .timestamp(timestamp)
                .source(source)
                .build();
    }

    @Value.Check
    protected void check() {
        checkArgument(name().getBytes().length <= 100, "Metric name length must not be > 100 bytes");
        source().ifPresent(m -> {
            checkArgument(m.getBytes().length <= 100, "Source name length must not be > 100 bytes");
        });
    }
}
