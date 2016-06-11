package com.boundary.meter.client.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.net.HostAndPort;
import io.netty.handler.logging.LogLevel;

public class BoundaryRpcClientConfig {

    @JsonProperty
    private HostAndPort meter = HostAndPort.fromParts("localhost", 9192);

    @JsonProperty
    private boolean loggingEnabled = false;

    @JsonProperty
    private LogLevel logLevel = LogLevel.DEBUG;


    public void setMeter(HostAndPort meter) {
        this.meter = meter;
    }

    public HostAndPort getMeter() {
        return meter;
    }

    public void setLoggingEnabled(boolean loggingEnabled) {
        this.loggingEnabled = loggingEnabled;
    }

    public boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }
}
