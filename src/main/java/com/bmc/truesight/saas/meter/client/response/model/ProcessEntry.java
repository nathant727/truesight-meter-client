package com.bmc.truesight.saas.meter.client.response.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.Optional;

@JsonDeserialize(as = ImmutableProcessEntry.class)
@Value.Immutable
public abstract class ProcessEntry {

    public abstract int pid();
    public abstract Optional<Integer> ppid();
    public abstract String name();
    public abstract String arg0();
    public abstract double cpuPct();
    public abstract double memPct();
    public abstract long memSize();
    public abstract long memRss();
    public abstract long diskR();
    public abstract long diskW();

}
