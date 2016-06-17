package com.bmc.truesight.saas.meter.client.command;

import com.bmc.truesight.saas.meter.client.response.GetSystemInfoResponse;

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
    public Class<GetSystemInfoResponse> getResponseType() {
        return GetSystemInfoResponse.class;
    }

    @Override
    public String getMethod() {
        return "get_system_info";
    }
}
