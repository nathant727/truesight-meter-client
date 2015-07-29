package com.boundary.meter.client.response;

import com.boundary.meter.client.response.model.Method;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.List;

@JsonDeserialize(as = ImmutableDiscoveryResponse.class)
@Value.Immutable
public abstract class DiscoveryResponse implements Response {
    public abstract String meterVersion();
    public abstract List<Method> methods();
}
