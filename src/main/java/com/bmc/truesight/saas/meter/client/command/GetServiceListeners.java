package com.bmc.truesight.saas.meter.client.command;

import com.bmc.truesight.saas.meter.client.response.GetServiceListenersResponse;

public class GetServiceListeners implements Command<GetServiceListenersResponse> {


    private static final GetServiceListeners INSTANCE = new GetServiceListeners();

    private GetServiceListeners() {
        // singleton
    }

    public static GetServiceListeners of() {
        return INSTANCE;
    }

    @Override
    public Class<GetServiceListenersResponse> getResponseType() {
        return GetServiceListenersResponse.class;
    }

    @Override
    public String getMethod() {
        return "get_service_listeners";
    }

}
