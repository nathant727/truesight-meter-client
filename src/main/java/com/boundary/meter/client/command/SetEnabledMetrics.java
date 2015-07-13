package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;

/**
 * JSON RPC call to set enabled metrics
 */
public class SetEnabledMetrics implements Command<SetEnabledMetricsResponse> {

    public enum Type {
        core, custom
    }
    private ImmutableMap<String, Object> params;

    public SetEnabledMetrics(ImmutableMap<String, Object> params) {
        this.params = params;
    }

    public static SetEnabledMetrics of(Type type, boolean enabled) {
        return new SetEnabledMetrics(ImmutableMap.of("metrics", ImmutableMap.of(type.name(), ImmutableMap.of("isEnabled", enabled))));
    }

    @Override
    public SetEnabledMetricsResponse convertResponse(int id, JsonNode node) {
        return SetEnabledMetricsResponse.of(id, node);
    }

    @Override
    public String getMethod() {
        return "set_enabled_metrics";
    }

    @Override
    public ImmutableMap<String, Object> getParams() {
        return this.params;
    }
}
