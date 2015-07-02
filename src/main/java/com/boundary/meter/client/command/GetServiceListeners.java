package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;

import static com.google.common.base.Preconditions.checkState;

public class GetServiceListeners implements Command<GetServiceListenersResponse> {


    public static final GetServiceListeners INSTANCE = new GetServiceListeners(-1);
    private final int id;

    public GetServiceListeners(int id) {
        this.id = id;
    }

    @Override
    public GetServiceListenersResponse convertResponse(int id, JsonNode node) {
        return new GetServiceListenersResponse(id, node);
    }

    @Override
    public Command<GetServiceListenersResponse> withId(int id) {
        return new GetServiceListeners(id);
    }

    @Override
    public String getMethod() {
        return "get_service_listeners";
    }

    @Override
    public int getId() {
        checkState(id >= 0, "Invalid/uninitialized id");
        return id;
    }
}
