package com.bmc.truesight.saas.meter.client.command;

import com.bmc.truesight.saas.meter.client.response.GetProcessTopKResponse;
import com.google.common.collect.ImmutableMap;
import org.immutables.value.Value;

/**
 * JSON RPC call to retrieve and parse TopK Running Process Information
 */

public class GetProcessTopK implements Command<GetProcessTopKResponse> {


    @Value.Immutable
    public static abstract class TypedNumber {
        public enum Type {
            cpu, mem
        }

        @Value.Default
        public Type type() {
            return Type.cpu;
        }
        @Value.Default
        public int number() {
            return 10;
        }
    }

    private ImmutableMap<String, Object> params;

    public GetProcessTopK(ImmutableMap<String, Object> params) {
        this.params = params;
    }

    public static GetProcessTopK of(TypedNumber number1, TypedNumber ... optional) {
        ImmutableMap.Builder<String, Object> paramsBuilder = ImmutableMap.builder();

        paramsBuilder.put(number1.type().name(), number1.number());
        for (TypedNumber number: optional) {
            paramsBuilder.put(number.type().name(), number.number());
        }
        return new GetProcessTopK(paramsBuilder.build());
    }

    @Override
    public Class<GetProcessTopKResponse> getResponseType() {
        return GetProcessTopKResponse.class;
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
