package com.bmc.truesight.saas.meter.client.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@JsonDeserialize(as = ImmutableDebugResponse.class)
@Value.Immutable
public abstract class DebugResponse implements Response {
    public abstract String status();
}
