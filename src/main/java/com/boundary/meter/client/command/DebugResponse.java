package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;
import org.immutables.value.Value;

/**
 * Response parsing of Debug JSON RPC call
 */
@Value.Immutable
public abstract class DebugResponse implements Response {
    public abstract String status();

    public static DebugResponse of(int id, JsonNode resp) {

        JsonNode result = resp.get("result");
        return ImmutableDebugResponse.builder()
                .id(id)
                .status(result.get("status").asText())
                .build();

    }

    @Override
    public String toString() {
        return "DebugResponse{" +
                "status='" + status() + '\'' +
                ", id=" + id() +
                '}';
    }

}
