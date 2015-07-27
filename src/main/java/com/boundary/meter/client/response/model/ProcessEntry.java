package com.boundary.meter.client.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@JsonDeserialize(as = ImmutableProcessEntry.class)
@Value.Immutable
public abstract class ProcessEntry {

    public abstract int pid();
    public abstract int ppid();
    public abstract String name();
    public abstract String arg0();
    @JsonProperty("cpu_pct")
    public abstract double cpuPct();
    @JsonProperty("mem_pct")
    public abstract double memPct();
    @JsonProperty("mem_size")
    public abstract long memSize();
    @JsonProperty("mem_rss")
    public abstract long memRss();
    public abstract long diskr();
    public abstract long diskw();

}
