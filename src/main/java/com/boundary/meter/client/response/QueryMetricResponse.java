package com.boundary.meter.client.response;

import com.fasterxml.jackson.databind.JsonNode;
import org.immutables.value.Value;

/**
 * Read and parse the Query Metric JSON RPC responses
 */

@Value.Immutable
public abstract class QueryMetricResponse implements Response {
    public abstract String status();
    // TODO: create model class for metrics
    public abstract JsonNode metrics();
}
