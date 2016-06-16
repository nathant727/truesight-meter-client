package com.bmc.truesight.saas.meter.client.command;

import com.bmc.truesight.saas.meter.client.response.Response;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public interface Command<T extends Response> {

    @JsonIgnore
    Class<T> getResponseType();

    String getMethod();

    default String getJsonrpc() {
        return "2.0";
    }

    default Map<String, Object> getParams() {
        return ImmutableMap.of();
    }




}
