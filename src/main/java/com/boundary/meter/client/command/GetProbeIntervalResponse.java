package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;
import org.immutables.value.Value;

import java.util.Optional;

/**
 * Response parsing of Get Probe Interval JSON RPC call
 */
@Value.Immutable
public abstract class GetProbeIntervalResponse implements Response {

    public abstract String status();
    public abstract Optional<Long> cpu();
    public abstract Optional<Long> file();
    public abstract Optional<Long> mem();
    public abstract Optional<Long> net();
    public abstract Optional<Long> proc();
    public abstract Optional<Long> swap();
    public abstract Optional<Long> os();
    public abstract Optional<Long> user();
    public abstract Optional<Long> flow();


    public static GetProbeIntervalResponse of(int id, JsonNode resp) {

        ImmutableGetProbeIntervalResponse.Builder probeBuilder = ImmutableGetProbeIntervalResponse.builder();
        JsonNode result = resp.get("result");

        probeBuilder.id(id);
        probeBuilder.status(result.get("status").asText());
        if (result.get("status").asText().equalsIgnoreCase("OK") && result.has("probe")) {
            JsonNode probe = result.get("probe");

            if (probe.has("cpu")) {
                probeBuilder.cpu(probe.get("cpu").asLong());
            } else if (probe.has("file")) {
                probeBuilder.file(probe.get("file").asLong());
            } else if (probe.has("mem")) {
                probeBuilder.mem(probe.get("mem").asLong());
            } else if (probe.has("net")) {
                probeBuilder.net(probe.get("net").asLong());
            } else if (probe.has("proc")) {
                probeBuilder.proc(probe.get("proc").asLong());
            } else if (probe.has("swap")) {
                probeBuilder.swap(probe.get("swap").asLong());
            } else if (probe.has("os")) {
                probeBuilder.os(probe.get("os").asLong());
            } else if (probe.has("user")) {
                probeBuilder.user(probe.get("user").asLong());
            } else if (probe.has("flow")) {
                probeBuilder.flow(probe.get("flow").asLong());
            }
        }
        return probeBuilder.build();

    }

    @Override
    public String toString() {
        return "GetProbeIntervalResponse{" +
                "status='" + status() + '\'' +
                ", id=" + id() +
                '}';
    }
}
