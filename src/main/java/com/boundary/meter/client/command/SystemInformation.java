package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * JSON RPC call to retrieve System Information
 */
public class SystemInformation implements Command<SystemInformationResponse> {
    public final static SystemInformation INSTANCE = new SystemInformation();

    private SystemInformation() {
        // singleton
    }

    @Override
    public SystemInformationResponse convertResponse(int id, JsonNode node) {

        return SystemInformationResponse.factory(id, node);
    }

    @Override
    public String getMethod() {
        return "system_info";
    }
}
