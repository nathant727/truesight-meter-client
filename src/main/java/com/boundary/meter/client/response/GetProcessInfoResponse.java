package com.boundary.meter.client.response;

import com.boundary.meter.client.response.model.ProcessEntry;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.List;

/**
 * Read and parse response from Process Information JSON RPC call
 */

@JsonDeserialize(as = ImmutableGetProcessInfoResponse.class)
@Value.Immutable
public abstract class GetProcessInfoResponse implements Response {

    public abstract String status();
    public abstract List<ProcessEntry> processes();

}
