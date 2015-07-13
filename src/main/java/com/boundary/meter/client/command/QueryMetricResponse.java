package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;
import org.immutables.value.Value;

import java.util.Iterator;

/**
 * Read and parse the Query Metric JSON RPC responses
 */

@Value.Immutable
public abstract class QueryMetricResponse implements Response {
    public abstract String status();
    public abstract JsonNode metrics();

    public static QueryMetricResponse of(int id, JsonNode resp) {
        ImmutableQueryMetricResponse.Builder metricBuilder = ImmutableQueryMetricResponse.builder();
        JsonNode result = resp.get("result");

        metricBuilder.id(id);
        metricBuilder.status(result.get("status").asText());
        if (result.get("status").asText().equalsIgnoreCase("OK")) {
            if (result.has("query_metric")) {
                metricBuilder.metrics(result.get("query_metric"));
            }
        }
        return metricBuilder.build();
    }

    @Override
    public String toString() {
        String returnVal = "QueryMetricResponse{" +
                "Status='" + status() + '\'' +
                ", id=" + id();

        if (status().equalsIgnoreCase("OK")) {
            if (metrics().isArray()) {
                Iterator<JsonNode> ite = metrics().elements();
                Integer i = 1;
                while (ite.hasNext()) {
                    String name = ite.next().asText();
                    String value = ite.next().asText();
                    String timestamp = ite.next().asText();
                    returnVal = returnVal +
                            ", Metric" + i.toString() + " Name='" + name + '\'' +
                            ", Metric" + i.toString() + " Value='" + value + '\'' +
                            ", Metric" + i.toString() + " Timestamp='" + timestamp + '\'';
                    i++;
                }
            } else {
                returnVal = returnVal + ", Metrics=" + metrics();
            }
        }
        returnVal = returnVal + "}";
        return returnVal;
    }

}
