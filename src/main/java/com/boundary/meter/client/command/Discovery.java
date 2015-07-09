package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;


public class Discovery implements Command<DiscoveryResponse> {

    private final static Discovery INSTANCE = new Discovery();

    private Discovery() {
        // singleton
    }

    public static Discovery of() {
        return INSTANCE;
    }

    @Override
    public DiscoveryResponse convertResponse(int id, JsonNode node) {
        return DiscoveryResponse.of(id, node);
    }

    @Override
    public String getMethod() {
        return "discovery";
    }

}
