package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;
import org.immutables.value.Value;

/**
 * Response parsing of Set API Interval JSON RPC call
 */

@Value.Immutable
public abstract class SetAPIIntervalsResponse implements Response {

    public abstract String status();

    public static SetAPIIntervalsResponse of(int id, JsonNode resp) {

        ImmutableSetAPIIntervalsResponse.Builder probeBuilder = ImmutableSetAPIIntervalsResponse.builder();
        JsonNode result = resp.get("result");

        probeBuilder.id(id);
        probeBuilder.status(result.get("status").asText());

        return probeBuilder.build();
    }

}
