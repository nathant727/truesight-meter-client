package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;

/**
 * JSON RPC call to retrieve probe intervals
 */
public class GetProbeInterval implements Command<GetProbeIntervalResponse> {

    public enum Type {
        cpu, file, mem, net, proc, swap, os, user, flow
    }

    private ImmutableMap<String, Object> params;

    public GetProbeInterval(ImmutableMap<String, Object> params) {
        this.params = params;
    }

    public static GetProbeInterval of(Type probe) {
        return new GetProbeInterval(ImmutableMap.of("probe", probe.name()));
    }

    @Override
    public GetProbeIntervalResponse convertResponse(int id, JsonNode node) {
        return GetProbeIntervalResponse.of(id, node);
    }

    @Override
    public String getMethod() {
        return "get_probe_interval";
    }

    @Override
    public ImmutableMap<String, Object> getParams() {
        return this.params;
    }

}
