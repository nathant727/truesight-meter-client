package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;

/**
 * JSON RPC call to set probe intervals
 */
public class SetProbeInterval implements Command<SetProbeIntervalResponse> {

    private ImmutableMap<String, Object> params;

    public SetProbeInterval(ImmutableMap<String, Object> params) {
        this.params = params;
    }

    public static SetProbeInterval of(GetProbeInterval.Type probe, long ms) {
        return new SetProbeInterval(ImmutableMap.of("probe", probe.name(), "ms", ms));
    }

    @Override
    public SetProbeIntervalResponse convertResponse(int id, JsonNode node) {
        return SetProbeIntervalResponse.of(id, node);
    }

    @Override
    public String getMethod() {
        return "set_probe_interval";
    }

    @Override
    public ImmutableMap<String, Object> getParams() {
        return this.params;
    }

}
