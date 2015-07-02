package com.boundary.meter.client.command;

public class VoidResponse implements Response{

    private final int id;

    public VoidResponse(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }
}
