package com.boundary.meter.client.rpc;

import com.boundary.meter.client.command.Command;

import java.util.Optional;

public class DisconnectedException extends Exception {
    private final Optional<Command> command;

    public DisconnectedException(String msg, Command command) {
        this(msg, Optional.of(command));
    }

    public DisconnectedException(String msg) {
        this(msg, Optional.empty());
    }

    public DisconnectedException(String msg, Optional<Command> command) {
        super(msg);
        this.command = command;
    }

    public Optional<Command> getCommand() {
        return command;
    }
}
