package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;
import org.immutables.value.Value;

/**
 * Response parsing of Set Enabled Metrics JSON RPC call
 */

@Value.Immutable
public abstract class SetEnabledMetricsResponse implements Response {

    public abstract String status();

    public static SetEnabledMetricsResponse of(int id, JsonNode resp) {

        ImmutableSetEnabledMetricsResponse.Builder probeBuilder = ImmutableSetEnabledMetricsResponse.builder();
        JsonNode result = resp.get("result");

        probeBuilder.id(id);
        probeBuilder.status(result.get("status").asText());

        return probeBuilder.build();
    }
}
