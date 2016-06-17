package com.bmc.truesight.saas.meter.client.response;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

/**
 * Read and parse the Query Metric JSON RPC responses
 */

@JsonDeserialize(as = ImmutableQueryMetricResponse.class)
@Value.Immutable
public abstract class QueryMetricResponse implements Response {
    public abstract String status();
    // TODO: create model class for metrics objects & deserialize to that
    //{"status":"Ok","query_metric":["BAR.FOO",-0.479426,1437058,"BAR.BAZ",0.500000,1437055]}}
    public abstract JsonNode query_metric();
}
