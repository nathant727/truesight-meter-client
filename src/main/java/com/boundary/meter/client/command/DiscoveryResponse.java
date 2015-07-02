package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;

public class DiscoveryResponse implements Response {

    private final String meterVersion;
    private final int id;
    private final JsonNode methods;

    public DiscoveryResponse(int id, JsonNode resp) {
        this.id = id;
        JsonNode result = resp.get("result");
        this.meterVersion = result.get("meter_version").asText();
        this.methods = result.get("methods");

    }

    @Override
    public String toString() {
        return "DiscoveryResponse{" +
                "meterVersion='" + meterVersion + '\'' +
                ", id=" + id +
                ", methods=" + methods +
                '}';
    }

    @Override
    public int getId() {
        return id;
    }
}
