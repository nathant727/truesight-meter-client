package com.boundary.meter.client.model;

import org.immutables.value.Value;

/**
 * Immutable representation of a single measurement
 */
@Value.Immutable
public abstract class Measure {
    @Value.Parameter
    public abstract String name();
    @Value.Parameter
    public abstract double value();
}
