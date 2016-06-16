package com.bmc.truesight.saas.meter.client.command;

import com.bmc.truesight.saas.meter.client.response.VoidResponse;

public abstract class VoidCommand implements Command<VoidResponse> {

    @Override
    public Class<VoidResponse> getResponseType() {
        return VoidResponse.class;
    }
}
