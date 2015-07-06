package com.boundary.meter.client;

import com.boundary.meter.client.command.DiscoveryResponse;
import com.boundary.meter.client.command.GetServiceListenersResponse;
import com.boundary.meter.client.command.VoidResponse;
import com.boundary.meter.client.model.Measure;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface BoundaryMeterClient extends AutoCloseable {

    CompletableFuture<VoidResponse> addMeasures(List<Measure> metrics);

    CompletableFuture<DiscoveryResponse> discovery();

    CompletableFuture<GetServiceListenersResponse> getServiceListeners();
}
