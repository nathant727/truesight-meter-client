package com.boundary.meter.client;

import com.boundary.meter.client.command.DiscoveryResponse;
import com.boundary.meter.client.command.GetServiceListenersResponse;
import com.boundary.meter.client.command.VoidResponse;
import com.boundary.meter.client.model.Measure;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

public interface BoundaryMeterClient extends AutoCloseable {

    ListenableFuture<VoidResponse> addMeasures(List<Measure> metrics);

    ListenableFuture<DiscoveryResponse> discovery();

    ListenableFuture<GetServiceListenersResponse> getServiceListeners();
}
