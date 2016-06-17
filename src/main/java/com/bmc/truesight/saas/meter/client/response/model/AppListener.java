package com.bmc.truesight.saas.meter.client.response.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@JsonDeserialize(as = ImmutableAppListener.class)
@Value.Immutable
public abstract class AppListener {

    public abstract int port();
    public abstract String proto();
    public abstract String process();

}
