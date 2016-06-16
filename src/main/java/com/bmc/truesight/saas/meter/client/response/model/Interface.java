package com.bmc.truesight.saas.meter.client.response.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.List;
import java.util.Optional;

@JsonDeserialize(as = ImmutableInterface.class)
@Value.Immutable
public abstract class Interface {

    public abstract String name();
    public abstract String type();
    public abstract int mtu();
    public abstract Optional<String> ether();

    public abstract Optional<String> driverName();

    public abstract Optional<String> driverVersion();

    public abstract Optional<String>  firmwareVersion();

    // todo parse these -- "addrs":["127.0.0.1/8","::1","fe80:1::1"]
    public abstract List<String> addrs();

    public abstract int flagBits();

    public abstract List<String> flags();

}
