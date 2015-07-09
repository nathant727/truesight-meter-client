package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;

/**
 * JSON RPC call to modify debug levels
 */
public class Debug implements Command<DebugResponse> {

    private ImmutableMap<String, Object> params;


    public Debug(String section, int level) {
        this(ImmutableMap.of("section", section, "level", level));
    }

    public Debug(ImmutableMap<String, Object> params) {
        this.params = params;
    }

    public static Debug of(String section, int level) {
        return new Debug(section, level);
    }

    @Override
    public DebugResponse convertResponse(int id, JsonNode node) {
        return DebugResponse.factory(id, node);
    }

    @Override
    public String getMethod() {
        return "debug";
    }
}
