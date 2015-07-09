package com.boundary.meter.client.model;

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


    public enum Type {
        info, warn, error, critical
    }

    public abstract String title();
    @Value.Default
    public Type type() {
        return Type.info;
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

    public static Event of(String title, Type type) {
        return ImmutableEvent.builder()
                .title(title)
                .type(type)
                .build();
    }

    public static Event of(String title, Instant timestamp) {
        return ImmutableEvent.builder()
                .title(title)
                .timestamp(timestamp)
                .build();
    }

    public static Event of(String title, Type type, Instant timestamp) {
        return ImmutableEvent.builder()
                .title(title)
                .type(type)
                .timestamp(timestamp)
                .build();
    }

    public static Event of(String title, Optional<String> message,
                           Optional<String> source, ImmutableSet<String> tags,
                           Optional<String> at, Optional<String> ad,
                           Optional<String> sender) {
        return ImmutableEvent.builder()
                .title(title)
                .message(message)
                .source(source)
                .tags(tags)
                .at(at)
                .ad(ad)
                .sender(sender)
                .build();
    }

    public static Event of(String title, Type type,
                           Instant timestamp, Optional<String> message,
                           Optional<String> source, ImmutableSet<String> tags,
                           Optional<String> at, Optional<String> ad,
                           Optional<String> sender) {
        return ImmutableEvent.builder()
                .title(title)
                .type(type)
                .timestamp(timestamp)
                .message(message)
                .source(source)
                .tags(tags)
                .at(at)
                .ad(ad)
                .sender(sender)
                .build();
    }

    public static Event of(String title, Type type, Optional<String> message,
                           Optional<String> source, ImmutableSet<String> tags,
                           Optional<String> at, Optional<String> ad,
                           Optional<String> sender) {
        return ImmutableEvent.builder()
                .title(title)
                .type(type)
                .message(message)
                .source(source)
                .tags(tags)
                .at(at)
                .ad(ad)
                .sender(sender)
                .build();
    }

    public static Event of(String title, Instant timestamp, Optional<String> message,
                           Optional<String> source, ImmutableSet<String> tags,
                           Optional<String> at, Optional<String> ad,
                           Optional<String> sender) {
        return ImmutableEvent.builder()
                .title(title)
                .timestamp(timestamp)
                .message(message)
                .source(source)
                .tags(tags)
                .at(at)
                .ad(ad)
                .sender(sender)
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
