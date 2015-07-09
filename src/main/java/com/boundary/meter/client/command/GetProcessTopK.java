package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;

/**
 * JSON RPC call to retrieve and parse TopK Running Process Information
 */

public class GetProcessTopK implements Command<GetProcessTopKResponse> {
    public enum Type {
        cpu, mem
    }

    private ImmutableMap<String, Object> params;

    public GetProcessTopK(int number, Type type) {
        this(ImmutableMap.of(type.toString(), number));
    }

    public static GetProcessTopK of(int number, Type type) {
        return new GetProcessTopK(number, type);
    }

    public GetProcessTopK(int number, Type type, int number2, Type type2) {
        this(ImmutableMap.of(type.toString(), number, type2.toString(), number2));
    }

    public static GetProcessTopK of(int number, Type type, int number2, Type type2) {
        return new GetProcessTopK(number, type, number2, type2);
    }
    
    public GetProcessTopK(ImmutableMap<String, Object> params) {
        this.params = params;
    }

    @Override
    public GetProcessTopKResponse convertResponse(int id, JsonNode node) {
        return GetProcessTopKResponse.of(id, node);
    }

    @Override
    public String getMethod() {
        return "get_process_topk";
    }
}
