package com.boundary.meter.client.response;

import com.boundary.meter.client.response.model.AppListener;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.List;

@JsonDeserialize(as = ImmutableGetServiceListenersResponse.class)
@Value.Immutable
public abstract class GetServiceListenersResponse implements Response {

    public abstract String status();
    @JsonProperty("app_listeners")
    public abstract List<AppListener> listeners();

}
