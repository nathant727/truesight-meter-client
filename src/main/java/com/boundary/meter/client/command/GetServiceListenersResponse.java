package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;
import org.immutables.value.Value;

@Value.Immutable
public abstract class GetServiceListenersResponse implements Response {

    public abstract String status();
    public abstract JsonNode listeners();

    public static GetServiceListenersResponse of(int id, JsonNode resp) {
        JsonNode result = resp.get("result");
        if (result.get("status").asText().equalsIgnoreCase("OK")) {
            return ImmutableGetServiceListenersResponse.builder()
                    .id(id)
                    .status("OK")
                    .listeners(result.get("app_listeners"))
                    .build();
        } else {
            return ImmutableGetServiceListenersResponse.builder()
                    .id(id)
                    .status(result.get("status").asText())
                    .build();
        }
    }

    @Override
    public String toString() {
        return "GetServiceListenersResponse{" +
                "id=" + id() +
                ", listeners=" + listeners() +
                '}';
    }

}
