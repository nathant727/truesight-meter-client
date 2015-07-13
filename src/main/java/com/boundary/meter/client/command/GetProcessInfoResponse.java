package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;
import org.immutables.value.Value;

/**
 * Read and parse response from Process Information JSON RPC call
 */

@Value.Immutable
public abstract class GetProcessInfoResponse implements Response {

    public abstract String status();
    public abstract JsonNode processes();

    public static GetProcessInfoResponse of(int id, JsonNode resp) {
        ImmutableGetProcessInfoResponse.Builder infoBuilder = ImmutableGetProcessInfoResponse.builder();
        JsonNode result = resp.get("result");

        infoBuilder.id(id);
        infoBuilder.status(result.get("status").asText());
        if (result.get("status").asText().equalsIgnoreCase("OK")) {
            if (result.has("processes")) {
                infoBuilder.processes(result.get("processes"));
            }
        }
        return infoBuilder.build();
    }
}
