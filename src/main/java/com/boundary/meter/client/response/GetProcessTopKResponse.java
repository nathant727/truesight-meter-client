package com.boundary.meter.client.response;

import com.boundary.meter.client.response.model.TopKEntry;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.List;
import java.util.Map;

@JsonDeserialize(as = ImmutableGetProcessTopKResponse.class)
@Value.Immutable
public abstract class GetProcessTopKResponse implements Response {

    public abstract String status();

    public abstract Map<String, List<TopKEntry>> processes();

    public List<TopKEntry> cpu_topk() {
        return processes().get("cpu_topk");
    }
    public List<TopKEntry> mem_topk() {
        return processes().get("mem_topk");
    }

}
