package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;

public class GetServiceListenersResponse implements Response {

    private final int id;
    private final JsonNode node;

    public GetServiceListenersResponse(int id, JsonNode node) {
        this.id = id;
        this.node = node;
    }

    @Override
    public String toString() {
        return "GetServiceListenersResponse{" +
                "id=" + id +
                ", node=" + node +
                '}';
    }

    @Override
    public int getId() {
        return id;
    }
}
