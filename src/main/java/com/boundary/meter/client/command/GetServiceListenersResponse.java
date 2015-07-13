package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;
import org.immutables.value.Value;

@Value.Immutable
public abstract class GetServiceListenersResponse implements Response {

    public abstract String status();
    public abstract JsonNode listeners();

    public static GetServiceListenersResponse of(int id, JsonNode resp) {
        ImmutableGetServiceListenersResponse.Builder listenerBuilder = ImmutableGetServiceListenersResponse.builder();
        JsonNode result = resp.get("result");

        listenerBuilder.id(id);
        listenerBuilder.status(result.get("status").asText());
        if (result.get("status").asText().equalsIgnoreCase("OK")) {
            if (result.has("app_listeners")) {
                listenerBuilder.listeners(result.get("app_listeners"));
            }
        }
        return listenerBuilder.build();
    }

    @Override
    public String toString() {
        return "GetServiceListenersResponse{" +
                "id=" + id() +
                ", listeners=" + listeners() +
                '}';
    }

}
