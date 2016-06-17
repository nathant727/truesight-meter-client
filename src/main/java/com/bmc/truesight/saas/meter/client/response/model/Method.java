package com.bmc.truesight.saas.meter.client.response.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableMethod.class)
public abstract class Method {
    public abstract String method();
    public abstract String version();
    public abstract String description();
}

