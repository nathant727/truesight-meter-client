package com.boundary.meter.client.command;

import com.boundary.meter.client.Json;
import com.fasterxml.jackson.databind.JsonNode;
import org.immutables.value.Value;

import java.util.Optional;

/**
 * Read and parse response from TopK Process Information JSON RPC call
 */

@Value.Immutable
public abstract class GetProcessTopKResponse implements Response {

    public abstract String status();
    public abstract Optional<JsonNode> cpu_topk();
    public abstract Optional<JsonNode> mem_topk();

    public static GetProcessTopKResponse of(int id, JsonNode resp) {
        JsonNode result = resp.get("result");

        if (result.get("status").asText().equalsIgnoreCase("OK")) {
            JsonNode processes = result.get("processes");

            if (processes.has("cpu_topk")) {
                if (processes.has("mem_topk")) {
                    return ImmutableGetProcessTopKResponse.builder()
                            .id(id)
                            .status("OK")
                            .cpu_topk(processes.get("cpu_topk"))
                            .mem_topk(processes.get("mem_topk"))
                            .build();
                } else {
                    return ImmutableGetProcessTopKResponse.builder()
                            .id(id)
                            .status("OK")
                            .cpu_topk(processes.get("cpu_topk"))
                            .build();
                }
            } else {
                return ImmutableGetProcessTopKResponse.builder()
                        .id(id)
                        .status("OK")
                        .mem_topk(processes.get("mem_topk"))
                        .build();
            }
        } else {
            return ImmutableGetProcessTopKResponse.builder()
                    .id(id)
                    .status(result.get("status").asText())
                    .build();
        }
    }
}
