package com.boundary.meter.client.command;

import org.immutables.value.Value;

@Value.Immutable
public abstract class VoidResponse implements Response {

    public static VoidResponse of(int id) {
        return ImmutableVoidResponse.builder()
                .id(id)
                .build();
    }

}
