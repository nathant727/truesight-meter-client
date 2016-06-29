package com.bmc.truesight.saas.meter.client.model;

import com.google.common.collect.ImmutableSet;
import org.immutables.value.Value;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Immutable representation of a single event
 */
@Value.Immutable
public abstract class Event {

    /**
     * Used to describe the type of event.  Possible values:<UL>
     * <LI><CODE>Severity.info</CODE></LI>
     * <LI><CODE>Severity.warn</CODE></LI>
     * <LI><CODE>Severity.error</CODE></LI>
     * <LI><CODE>Severity.critical</CODE></LI>
     * </UL>
     */
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
    public abstract Optional<Map<String, String>> properties();

    /**
     * Creates an Event with the given required parameter: title.
     *
     * @param title the Title of this event (must not be greater than 255 characters long).
     * @return a new Immutable Event built with the title provided.
     */
    public static Event of(String title) {
        return ImmutableEvent.builder()
                .title(title)
                .build();
    }

    /**
     * Creates an Event with the given required parameters: title and severity.
     *
     * @param title    the Title of this event (must not be greater than 255 characters long).
     * @param severity the Severity of the event (info, warn, error or critical)
     * @return a new Immutable Event built with the title and severity provided.
     */
    public static Event of(String title, Severity severity) {
        return ImmutableEvent.builder()
                .title(title)
                .severity(severity)
                .build();
    }

    /**
     * Creates an Event with the given required parameters: title, severity and message.
     *
     * @param title    the Title of this event (must not be greater than 255 characters long).
     * @param severity the Severity of the event (info, warn, error or critical)
     * @param message  a message for this event (must not be greater than 255 characters long).
     * @return a new Immutable Event built with the title, severity and message provided.
     */
    public static Event of(String title, Severity severity, String message) {
        return ImmutableEvent.builder()
                .title(title)
                .severity(severity)
                .message(message)
                .build();
    }

    /**
     * Creates an Event with the given required parameters: title, severity, message and properties.
     *
     * @param title      the Title of this event (must not be greater than 255 characters long).
     * @param severity   the Severity of the event (info, warn, error or critical)
     * @param message    a message for this event (must not be greater than 255 characters long).
     * @param properties a Map of properties Key and Values, must not be null
     * @return a new Immutable Event built with the title, severity, message and properties provided.
     */
    public static Event of(String title, Severity severity, String message, Map<String, String> properties) {
        return ImmutableEvent.builder()
                .title(title)
                .severity(severity)
                .message(message)
                .properties(properties)
                .build();
    }

    /**
     * Creates an Event with the given title, severity, message, properties, senderRef, sourceRef, timestamp, tags, at and ad.
     *
     * @param title      the Title of this event (must not be greater than 255 characters long).  Required.
     * @param severity   the Severity of the event (info, warn, error or critical). Defaults to info if not specified.
     * @param message    a message for this event, must not be greater than 255 characters long (if not null).
     * @param properties a Map of properties Key and Values. Can be null.
     * @param sourceRef  the source of this event, can be null. If null defaults to the Fully Qualified Domain Name of the meter host.
     * @param senderRef  the sender of this event, can be null. If null the meter determines this value.
     * @param timestamp  time of when this Event was created, can be null. If null defaults to <a href="https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html#now--">Instant.now()</a>.
     * @param tags       a Set of String values - specifically an com.google.common.collect.ImmutableSet &lt;String&gt;.  Can be null.
     * @param at         App Data Type - Opaque field that is application specific.  Can be used to provide type for app data. Can be null.
     * @param ad         App Data - Application specific data.  In most cases this should be base64 encoded. Can be null.
     * @return a new Immutable Event built with the title, severity, message, properties, senderRef, sourceRef, timestamp, tags, at and ad provided.
     */
    public static Event of(String title, Severity severity, String message, Map<String, String> properties, String sourceRef, String senderRef, Instant timestamp, ImmutableSet<String> tags, String at, String ad) {
        ImmutableEvent.Builder immutableEventBuilder = ImmutableEvent.builder().title(title);
        if (severity == null) {
            immutableEventBuilder = immutableEventBuilder.severity(Severity.info);
        } else {
            immutableEventBuilder = immutableEventBuilder.severity(severity);
        }

        if (message != null) {
            immutableEventBuilder = immutableEventBuilder.message(message);
        }

        if (properties != null) {
            immutableEventBuilder = immutableEventBuilder.properties(properties);
        }

        if (sourceRef != null) {
            immutableEventBuilder = immutableEventBuilder.source(sourceRef);
        }

        if (senderRef != null) {
            immutableEventBuilder = immutableEventBuilder.sender(senderRef);
        }

        if (timestamp != null) {
            immutableEventBuilder = immutableEventBuilder.timestamp(timestamp);
        } else {
            immutableEventBuilder = immutableEventBuilder.timestamp(Instant.now());
        }

        if (tags != null) {
            immutableEventBuilder = immutableEventBuilder.tags(tags);
        }

        if (ad != null) {
            immutableEventBuilder = immutableEventBuilder.ad(ad);
        }

        if (at != null) {
            immutableEventBuilder = immutableEventBuilder.at(at);
        }

        return immutableEventBuilder.build();
    }

    @Value.Check
    protected void check() {
        checkArgument(title().getBytes().length <= 255, "Title length must not be > 255 bytes");
        message().ifPresent(m -> {
            checkArgument(m.getBytes().length <= 255, "Message length must not be > 255 bytes");
        });
    }
}
