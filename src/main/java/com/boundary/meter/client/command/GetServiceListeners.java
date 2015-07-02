package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;

public class GetServiceListeners implements Command<GetServiceListenersResponse> {


    public static final GetServiceListeners INSTANCE = new GetServiceListeners();

    public GetServiceListeners() {

    }

    @Override
    public GetServiceListenersResponse convertResponse(int id, JsonNode node) {
        return new GetServiceListenersResponse(id, node);
    }

    @Override
    public String getMethod() {
        return "get_service_listeners";
    }

}
