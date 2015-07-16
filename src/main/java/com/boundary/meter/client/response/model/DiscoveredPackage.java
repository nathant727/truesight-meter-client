package com.boundary.meter.client.response.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@JsonDeserialize(as = ImmutableDiscoveredPackage.class)
@Value.Immutable
public abstract class DiscoveredPackage {

    public abstract String name();
    public abstract String version();

}
