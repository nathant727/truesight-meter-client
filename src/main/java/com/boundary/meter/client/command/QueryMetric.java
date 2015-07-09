package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;

/**
 * Implements Query Metric JSON RPC calls
 */
public class QueryMetric implements Command<QueryMetricResponse> {

    private ImmutableMap<String, Object> params;

    public QueryMetric(String metric, boolean Exact) {
        this(ImmutableMap.of(Exact ? "name" : "match", metric));
    }

    public QueryMetric(ImmutableMap<String, Object> params) {
        this.params = params;
    }

    public static QueryMetric of(String metric, boolean Exact) {
        return new QueryMetric(metric, Exact);
    }

    @Override
    public QueryMetricResponse convertResponse(int id, JsonNode node) {
        return QueryMetricResponse.of(id, node);
    }

    @Override
    public String getMethod() {
        return "query_metric";
    }
}
