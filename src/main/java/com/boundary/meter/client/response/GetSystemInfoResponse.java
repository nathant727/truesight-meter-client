package com.boundary.meter.client.response;

import com.boundary.meter.client.response.model.AppListener;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.List;
import java.util.Optional;

/**
 * Read and parse response from System Information JSON RPC call
 */


@JsonDeserialize(as = ImmutableGetSystemInfoResponse.class)
@Value.Immutable
public abstract class GetSystemInfoResponse implements Response {

    public abstract String meterVersion();
    public abstract String hostname();
    public abstract Optional<String> mach();
    public abstract Optional<String> osver();
    public abstract Optional<String> machdesc();
    public abstract Optional<String> osname();
    public abstract Optional<String> arch();
    public abstract Optional<String> version();
    public abstract Optional<String> vendname();
    public abstract Optional<String> patch();
    // TODO create model classes for remaining jsonNodes
    public abstract List<JsonNode> cpus();
    public abstract List<JsonNode> filesystems();
    public abstract Optional<JsonNode> memory();
    public abstract Optional<JsonNode> interfaces();
    @JsonProperty("discovered_packages")
    public abstract List<JsonNode> packages();
    @JsonProperty("app_listeners")
    public abstract List<AppListener> listeners();

}
