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
        JsonNode result = resp.get("result");

        if (result.get("status").asText().equalsIgnoreCase("OK")) {
            return ImmutableGetProcessInfoResponse.builder()
                    .id(id)
                    .status("OK")
                    .processes(result.get("processes"))
                    .build();
        } else {
            return ImmutableGetProcessInfoResponse.builder()
                    .id(id)
                    .status(result.get("status").asText())
                    .build();
        }
    }
}
