package com.bmc.truesight.saas.meter.client.model;

import com.google.common.collect.ImmutableSet;
import org.immutables.value.Value;

import java.time.Instant;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Immutable representation of a single event
 */
@Value.Immutable
public abstract class Event {


    public enum Severity {
        info, warn, error, critical
    }

    public abstract String title();
    @Value.Default
    public Severity severity() {
        return Severity.info;
    }
    @Value.Default
    public Instant timestamp() {
        return Instant.now();
    }
    public abstract Optional<String> message();
    public abstract Optional<String> source();
    public abstract ImmutableSet<String> tags();
    public abstract Optional<String> at();
    public abstract Optional<String> ad();
    public abstract Optional<String> sender();

    public static Event of(String title) {
        return ImmutableEvent.builder()
                .title(title)
                .build();
    }

    public static Event of(String title, Severity severity) {
        return ImmutableEvent.builder()
                .title(title)
                .severity(severity)
                .build();
    }

    public static Event of(String title, Severity severity, String message) {
        return ImmutableEvent.builder()
                .title(title)
                .severity(severity)
                .message(message)
                .build();
    }

    @Value.Check
    protected void check() {
        checkArgument(title().getBytes().length <= 255, "Title length must not be > 255 bytes");
        message().ifPresent(m -> {
            checkArgument(m.getBytes().length <= 255, "Message length must not be > 255 bytes");
        });
    }
}
