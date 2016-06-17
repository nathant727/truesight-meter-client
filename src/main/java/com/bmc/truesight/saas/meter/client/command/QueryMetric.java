package com.bmc.truesight.saas.meter.client.command;

import com.bmc.truesight.saas.meter.client.response.QueryMetricResponse;
import com.google.common.collect.ImmutableMap;

/**
 * Implements Query Metric JSON RPC calls
 */
public class QueryMetric implements Command<QueryMetricResponse> {

    private ImmutableMap<String, Object> params;

    public QueryMetric(ImmutableMap<String, Object> params) {
        this.params = params;
    }

    public static QueryMetric of(String metric, boolean exact) {
        return new QueryMetric(ImmutableMap.of(exact ? "name" : "match", metric));
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
