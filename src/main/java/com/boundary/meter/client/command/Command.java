package com.boundary.meter.client.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public interface Command<T extends Response> {


    @JsonIgnore
    T convertResponse(int id, JsonNode node);

    @JsonIgnore
    Command<T> withId(int id);

    String getMethod();

    int getId();

    default String getJsonrpc() {
        return "2.0";
    }

    default Map<String, Object> getParams() {
        return ImmutableMap.of();
    }




}
