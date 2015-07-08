package com.boundary.meter.client;

import com.boundary.meter.client.command.*;
import com.boundary.meter.client.model.Event;
import com.boundary.meter.client.model.Measure;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface BoundaryMeterClient extends AutoCloseable {

    CompletableFuture<VoidResponse> addMeasures(List<Measure> measures);

    CompletableFuture<VoidResponse> addMeasure(Measure measure);

    CompletableFuture<VoidResponse> addEvents(List<Event> events);

    CompletableFuture<VoidResponse> addEvent(Event event);

    CompletableFuture<DiscoveryResponse> discovery();

    CompletableFuture<DebugResponse> debug();

    CompletableFuture<SystemInformationResponse> systemInformation();

    CompletableFuture<GetServiceListenersResponse> getServiceListeners();
}
