package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;
import org.immutables.value.Value;

/**
 * Response parsing of Set Probe Interval JSON RPC call
 */

@Value.Immutable
public abstract class SetProbeIntervalResponse implements Response {
    public abstract String status();

    public static SetProbeIntervalResponse of(int id, JsonNode resp) {

        ImmutableSetProbeIntervalResponse.Builder probeBuilder = ImmutableSetProbeIntervalResponse.builder();
        JsonNode result = resp.get("result");

        probeBuilder.id(id);
        probeBuilder.status(result.get("status").asText());

        return probeBuilder.build();
    }
}
