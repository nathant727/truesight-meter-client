package com.boundary.meter.client.command;

import com.boundary.meter.client.response.DebugResponse;
import com.google.common.collect.ImmutableMap;

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
    public Class<DebugResponse> getResponseType() {
        return DebugResponse.class;
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
