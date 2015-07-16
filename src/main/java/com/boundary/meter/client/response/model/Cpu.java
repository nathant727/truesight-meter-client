package com.boundary.meter.client.response.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@JsonDeserialize(as = ImmutableCpu.class)
@Value.Immutable
public abstract class Cpu {

    public abstract String vendor();
    public abstract String model();
    public abstract int mhz();
    public abstract int cacheSize();
    public abstract int totalSockets();
    public abstract int totalCores();
    public abstract int coresPerSocket();

}
