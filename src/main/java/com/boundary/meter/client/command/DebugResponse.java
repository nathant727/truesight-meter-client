package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Response parsing of Debug JSON RPC call
 */
public class DebugResponse implements Response {
    private final String meterVersion;
    private final int id;
    private final JsonNode methods;

    public DebugResponse(int id, JsonNode resp) {
        this.id = id;
        JsonNode result = resp.get("result");
        this.meterVersion = result.get("meter_version").asText();
        this.methods = result.get("methods");

    }

    @Override
    public String toString() {
        return "DebugResponse{" +
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
