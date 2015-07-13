package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;

public class GetServiceListeners implements Command<GetServiceListenersResponse> {


    private static final GetServiceListeners INSTANCE = new GetServiceListeners();

    public GetServiceListeners() {

    }

    public static GetServiceListeners of() {
        return INSTANCE;
    }

    @Override
    public GetServiceListenersResponse convertResponse(int id, JsonNode node) {
        return GetServiceListenersResponse.of(id, node);
    }

    @Override
    public String getMethod() {
        return "get_service_listeners";
    }

}
