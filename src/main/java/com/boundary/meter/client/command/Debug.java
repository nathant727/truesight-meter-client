package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;

/**
 * JSON RPC call to modify debug levels
 */
public class Debug implements Command<DebugResponse> {
    public final static Debug INSTANCE = new Debug();

    private Debug() {
        // singleton
    }

    private ImmutableMap<String, Object> params;

    public void setParams(String section, int level) {
        this.params.putIfAbsent("section", section);
        this.params.putIfAbsent("level", level);
    }

    public void setParams(ImmutableMap<String, Object> params) {
        this.params = params;
    }

    @Override
    public DebugResponse convertResponse(int id, JsonNode node) {
        return new DebugResponse(id, node);
    }

    @Override
    public String getMethod() {
        return "debug";
    }
}
