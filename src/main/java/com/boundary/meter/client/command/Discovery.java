package com.boundary.meter.client.command;

import com.boundary.meter.client.response.DiscoveryResponse;

public class Discovery implements Command<DiscoveryResponse> {

    private final static Discovery INSTANCE = new Discovery();

    private Discovery() {
        // singleton
    }

    public static Discovery of() {
        return INSTANCE;
    }

    @Override
    public Class<DiscoveryResponse> getResponseType() {
        return DiscoveryResponse.class;
    }

    @Override
    public String getMethod() {
        return "discovery";
    }

}
