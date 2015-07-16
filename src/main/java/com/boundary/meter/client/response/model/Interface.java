package com.boundary.meter.client.response.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
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

    @JsonProperty("driver name")
    public abstract Optional<String> driverName();

    @JsonProperty("driver vers")
    public abstract Optional<String> driverVersion();

    @JsonProperty("firmware vers")
    public abstract Optional<String>  firmwareVersion();

    // todo parse these "addrs":["127.0.0.1/8","::1","fe80:1::1"]
    public abstract List<JsonNode> addrs();

    @JsonProperty("flag_bits")
    public abstract int flagBits();

    public abstract List<String> flags();

}
