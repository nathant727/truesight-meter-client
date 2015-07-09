package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * JSON RPC call to retrieve and
 */
public class GetSystemInfo implements Command<GetSystemInfoResponse> {
    private final static GetSystemInfo INSTANCE = new GetSystemInfo();

    private GetSystemInfo() {
        // singleton
    }

    public static GetSystemInfo of() {
        return INSTANCE;
    }

    @Override
    public GetSystemInfoResponse convertResponse(int id, JsonNode node) {

        return GetSystemInfoResponse.of(id, node);
    }

    @Override
    public String getMethod() {
        return "get_system_info";
    }
}
