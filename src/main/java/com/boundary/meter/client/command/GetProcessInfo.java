package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;

import java.util.Optional;

/**
 * JSON RPC call to retrieve and parse Running Process Information
 */

public class GetProcessInfo implements Command<GetProcessInfoResponse> {
    public enum Type {
        process, args_expr, cwd_expr
    }
    private ImmutableMap<String, Object> params;

    public GetProcessInfo(String expression, Type type) {
        this(ImmutableMap.of(type.toString(), expression));
    }

    public static GetProcessInfo of(String expression, Type type) {
        return new GetProcessInfo(expression, type);
    }

    public GetProcessInfo(String expression, Type type,
                          Optional<String> expression2, Optional<Type> type2,
                          Optional<String> expression3, Optional<Type> type3) {
        this(ImmutableMap.of(type.toString(), expression, type2.toString(), expression2, type3.toString(), expression3));
    }

    public static GetProcessInfo of(String expression, Type type,
                                    Optional<String> expression2, Optional<Type> type2,
                                    Optional<String> expression3, Optional<Type> type3) {
        return new GetProcessInfo(expression, type, expression2, type2, expression3, type3);
    }

    public GetProcessInfo(ImmutableMap<String, Object> params) {
        this.params = params;
    }

    @Override
    public GetProcessInfoResponse convertResponse(int id, JsonNode node) {

        return GetProcessInfoResponse.of(id, node);
    }

    @Override
    public String getMethod() {
        return "get_process_info";
    }
}