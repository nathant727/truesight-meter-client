package com.bmc.truesight.saas.meter.client.command;

import com.bmc.truesight.saas.meter.client.response.Response;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class Identified<T extends Response> {
    private final Command<T> command;
    private final int id;

    public Identified(Command<T> command, int id) {

        this.command = command;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Identified{" +
                "command=" + command +
                ", id=" + id +
                '}';
    }

    @JsonUnwrapped
    public Command<T> getCommand() {
        return command;
    }

    public int getId() {
        return id;
    }
}
