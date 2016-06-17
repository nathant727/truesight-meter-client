package com.bmc.truesight.saas.meter.client.rpc;

import com.bmc.truesight.saas.meter.client.command.Command;

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
