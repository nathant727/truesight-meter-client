package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;
import org.immutables.value.Value;

import java.util.Iterator;

@Value.Immutable
public abstract class DiscoveryResponse implements Response {

    public abstract String meterVersion();
    public abstract JsonNode methods();

    public static DiscoveryResponse of(int id, JsonNode resp) {

        JsonNode result = resp.get("result");
        return ImmutableDiscoveryResponse.builder()
                .id(id)
                .meterVersion(result.get("meter_version").asText())
                .methods(result.get("methods"))
                .build();

    }

    @Override
    public String toString() {
        String returnVal = "DiscoveryResponse{" +
                "meterVersion='" + meterVersion() + '\'' +
                ", id=" + id();
        if (methods().isArray()) {
            Iterator<JsonNode> ite = methods().elements();
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
