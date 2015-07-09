package com.boundary.meter.client;

import com.boundary.meter.client.command.*;
import com.boundary.meter.client.model.Event;
import com.boundary.meter.client.model.Measure;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface BoundaryMeterClient extends AutoCloseable {

    CompletableFuture<VoidResponse> addMeasures(List<Measure> measures);

    CompletableFuture<VoidResponse> addMeasure(Measure measure);

    CompletableFuture<VoidResponse> addEvents(List<Event> events);

    CompletableFuture<VoidResponse> addEvent(Event event);

    CompletableFuture<DiscoveryResponse> discovery();

    CompletableFuture<DebugResponse> debug(String section, int level);

    CompletableFuture<GetSystemInfoResponse> systemInformation();

    CompletableFuture<QueryMetricResponse> queryMetric(String metric, boolean Exact);

    CompletableFuture<GetProcessInfoResponse> getProcessInfo(String expression, GetProcessInfo.Type type);

    CompletableFuture<GetProcessInfoResponse> getProcessInfo(String expression, GetProcessInfo.Type type,
                                                             Optional<String> expression2, Optional<GetProcessInfo.Type> type2,
                                                             Optional<String> expression3, Optional<GetProcessInfo.Type> type3);

    CompletableFuture<GetProcessTopKResponse> getProcessTopK(int number, GetProcessTopK.Type type);

    CompletableFuture<GetProcessTopKResponse> getProcessTopK(int number, GetProcessTopK.Type type, int number2, GetProcessTopK.Type type2);

    CompletableFuture<GetServiceListenersResponse> getServiceListeners();
}
