package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * JSON RPC call to modify debug levels
 */
public class Debug implements Command<DebugResponse> {

    private ImmutableMap<String, Object> params;

    public Debug(ImmutableMap<String, Object> params) {
        this.params = params;
    }

    public static Debug of(String section, int level) {
        return new Debug(ImmutableMap.of("section", section, "level", level));
    }

    @Override
    public DebugResponse convertResponse(int id, JsonNode node) {
        return DebugResponse.of(id, node);
    }

    @Override
    public String getMethod() {
        return "debug";
    }

    @Override
    public ImmutableMap<String, Object> getParams() {
        return this.params;
    }
}
