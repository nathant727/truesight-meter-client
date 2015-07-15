package com.boundary.meter.client.command;

import com.boundary.meter.client.response.QueryMetricResponse;
import com.google.common.collect.ImmutableMap;

/**
 * Implements Query Metric JSON RPC calls
 */
public class QueryMetric implements Command<QueryMetricResponse> {

    private ImmutableMap<String, Object> params;

    public QueryMetric(ImmutableMap<String, Object> params) {
        this.params = params;
    }

    public static QueryMetric of(String metric, boolean Exact) {
        return new QueryMetric(ImmutableMap.of(Exact ? "name" : "match", metric));
    }

    @Override
    public Class<QueryMetricResponse> getResponseType() {
        return QueryMetricResponse.class;
    }

    @Override
    public String getMethod() {
        return "query_metric";
    }

    @Override
    public ImmutableMap<String, Object> getParams() {
        return this.params;
    }

}
