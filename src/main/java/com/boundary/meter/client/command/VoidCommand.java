package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;

public abstract class VoidCommand implements Command<VoidResponse> {

    @Override
    public final VoidResponse convertResponse(int id, JsonNode node) {
        throw new IllegalStateException("Void commands should not produce a response");
    }
}
