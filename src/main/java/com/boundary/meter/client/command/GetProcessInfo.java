package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import org.immutables.value.Value;

/**
 * JSON RPC call to retrieve and parse Running Process Information
 */

public class GetProcessInfo implements Command<GetProcessInfoResponse> {

    @Value.Immutable
    public static abstract class TypedExpression {
        public enum Type {
            process, args_expr, cwd_expr
        }

        @Value.Default
        public Type type() {
            return Type.process;
        }
        public abstract String expression();
    }

    private ImmutableMap<String, Object> params;

    public GetProcessInfo(ImmutableMap<String, Object> params) {
        this.params = params;
    }

    public static GetProcessInfo of(TypedExpression expression1, TypedExpression ... optional) {
        ImmutableMap.Builder<String, Object> paramsBuilder = ImmutableMap.builder();

        paramsBuilder.put(expression1.type().name(), expression1.expression());

        for (TypedExpression expression: optional) {
            paramsBuilder.put(expression.type().name(), expression.expression());
        }
        return new GetProcessInfo(paramsBuilder.build());
    }

    @Override
    public GetProcessInfoResponse convertResponse(int id, JsonNode node) {
        return GetProcessInfoResponse.of(id, node);
    }

    @Override
    public String getMethod() {
        return "get_process_info";
    }

    @Override
    public ImmutableMap<String, Object> getParams() {
        return this.params;
    }
}