package com.bmc.truesight.saas.meter.client.response;

import com.bmc.truesight.saas.meter.client.response.model.ProcessEntry;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.List;
import java.util.Map;

@JsonDeserialize(as = ImmutableGetProcessTopKResponse.class)
@Value.Immutable
public abstract class GetProcessTopKResponse implements Response {

    public abstract String status();

    public abstract Map<String, List<ProcessEntry>> processes();

    public List<ProcessEntry> cpu_topk() {
        return processes().get("cpuTopk");
    }
    public List<ProcessEntry> mem_topk() {
        return processes().get("memTopk");
    }

}
