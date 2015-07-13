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
        ImmutableGetProcessTopKResponse.Builder topkBuilder = ImmutableGetProcessTopKResponse.builder();
        JsonNode result = resp.get("result");

        topkBuilder.id(id);
        topkBuilder.status(result.get("status").asText());
        if (result.get("status").asText().equalsIgnoreCase("OK")) {
            if (result.has("processes")) {
                JsonNode processes = result.get("processes");

                if (processes.has("cpu_topk")) {
                    topkBuilder.cpu_topk(processes.get("cpu_topk"));
                }
                if (processes.has("mem_topk")) {
                    topkBuilder.mem_topk(processes.get("mem_topk"));
                }
            }
        }
        return topkBuilder.build();
    }
}
