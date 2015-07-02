package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;


public class Discovery implements Command<DiscoveryResponse> {

    public final static Discovery INSTANCE = new Discovery();

    private Discovery() {
        // singleton
    }

    @Override
    public DiscoveryResponse convertResponse(int id, JsonNode node) {
        return new DiscoveryResponse(id, node);
    }

    @Override
    public String getMethod() {
        return "discovery";
    }

}
