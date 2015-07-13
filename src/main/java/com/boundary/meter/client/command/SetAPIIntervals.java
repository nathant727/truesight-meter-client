package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import org.immutables.value.Value;

/**
 * JSON RPC call to set API intervals
 */

public class SetAPIIntervals implements Command<SetAPIIntervalsResponse> {

    @Value.Immutable
    public static abstract class TypedInterval {
        public enum Type {
            config, events, metrics, heartbeat, logs
        }

        @Value.Default
        public Type type() {
            return Type.config;
        }
        public abstract int interval();
    }

    private ImmutableMap<String, Object> params;

    public SetAPIIntervals(ImmutableMap<String, Object> params) {
        this.params = params;
    }

    public static SetAPIIntervals of(TypedInterval interval1, TypedInterval ... optional) {
        ImmutableMap.Builder<String, Object> paramsBuilder = ImmutableMap.builder();

        paramsBuilder.put(interval1.type().name(), interval1.interval());
        for (TypedInterval interval: optional) {
            paramsBuilder.put(interval.type().name(), interval.interval());
        }
        return new SetAPIIntervals(ImmutableMap.of("intervals", paramsBuilder.build()));
    }

    @Override
    public SetAPIIntervalsResponse convertResponse(int id, JsonNode node) {
        return SetAPIIntervalsResponse.of(id, node);
    }

    @Override
    public String getMethod() {
        return "get_process_topk";
    }

    @Override
    public ImmutableMap<String, Object> getParams() {
        return this.params;
    }

}
