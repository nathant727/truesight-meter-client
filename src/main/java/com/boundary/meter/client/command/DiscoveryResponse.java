package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;
import org.immutables.value.Value;

import java.util.Iterator;
import java.util.Optional;

@Value.Immutable
public abstract class DiscoveryResponse implements Response {

    public abstract String meterVersion();
    public abstract Optional<JsonNode> methods();

    public static DiscoveryResponse of(int id, JsonNode resp) {
        ImmutableDiscoveryResponse.Builder discoveryBuilder = ImmutableDiscoveryResponse.builder();
        JsonNode result = resp.get("result");

        discoveryBuilder.id(id);
        if (result.has("meter_version")) {
            discoveryBuilder.meterVersion(result.get("meter_version").asText());
        }
        if (result.has("methods")) {
            discoveryBuilder.methods(result.get("methods"));
        }

        return discoveryBuilder.build();

    }

    @Override
    public String toString() {
        String returnVal = "DiscoveryResponse{" +
                "meterVersion='" + meterVersion() + '\'' +
                ", id=" + id();
        if (methods().get().isArray()) {
            Iterator<JsonNode> ite = methods().get().elements();
            Integer i = 1;
            while (ite.hasNext()) {
                JsonNode method = ite.next();
                String methodName = method.get("method").asText();
                String version = method.get("version").asText();
                String description = method.get("description").asText();
                returnVal = returnVal +
                        ", Method" + i.toString() + " Name='" + methodName + '\'' +
                        ", Method" + i.toString() + " Version='" + version + '\'' +
                        ", Method" + i.toString() + " Description='" + description + '\'';
                i++;
            }
        } else {
            returnVal = returnVal + ", methods=" + methods();
        }
        returnVal = returnVal + '}';
        return returnVal;
    }

}
