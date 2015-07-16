package com.boundary.meter.client.response.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@JsonDeserialize(as = ImmutableMemory.class)
@Value.Immutable
public abstract class Memory {

    public abstract int installed();
    public abstract int usable();

}
