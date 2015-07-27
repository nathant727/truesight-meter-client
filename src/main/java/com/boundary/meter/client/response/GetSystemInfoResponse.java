package com.boundary.meter.client.response;

import com.boundary.meter.client.response.model.AppListener;
import com.boundary.meter.client.response.model.Cpu;
import com.boundary.meter.client.response.model.DiscoveredPackage;
import com.boundary.meter.client.response.model.FileSystem;
import com.boundary.meter.client.response.model.Interface;
import com.boundary.meter.client.response.model.Memory;
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
    public abstract List<Cpu> cpus();
    public abstract List<FileSystem> filesystems();
    public abstract Memory memory();
    public abstract List<Interface> interfaces();
    public abstract List<DiscoveredPackage> discoveredPackages();
    public abstract List<AppListener> appListeners();
}
