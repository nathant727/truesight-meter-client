package com.boundary.meter.client.command;

import com.boundary.meter.client.response.VoidResponse;

public abstract class VoidCommand implements Command<VoidResponse> {

    @Override
    public Class<VoidResponse> getResponseType() {
        return VoidResponse.class;
    }
}
