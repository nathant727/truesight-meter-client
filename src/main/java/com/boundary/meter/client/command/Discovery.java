package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;

import static com.google.common.base.Preconditions.checkState;


public class Discovery implements Command<DiscoveryResponse> {

    public final static Discovery INSTANCE = new Discovery(-1);

    private final int id;

    private Discovery(int id) {
        this.id = id;
    }

    @Override
    public DiscoveryResponse convertResponse(int id, JsonNode node) {
        return new DiscoveryResponse(id, node);
    }

    @Override
    public Command<DiscoveryResponse> withId(int id) {
        return new Discovery(id);
    }

    @Override
    public String getMethod() {
        return "discovery";
    }

    @Override
    public int getId() {
        checkState(id >= 0, "Invalid/uninitialized id");
        return id;
    }
}
