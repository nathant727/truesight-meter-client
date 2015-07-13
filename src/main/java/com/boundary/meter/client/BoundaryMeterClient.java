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

    CompletableFuture<GetProcessInfoResponse> getProcessInfo(GetProcessInfo.TypedExpression expression, GetProcessInfo.TypedExpression ... optional);

    CompletableFuture<GetProcessTopKResponse> getProcessTopK(GetProcessTopK.TypedNumber number, GetProcessTopK.TypedNumber ... optional);

    CompletableFuture<GetProbeIntervalResponse> getProbeInterval(GetProbeInterval.Type probe);

    CompletableFuture<SetProbeIntervalResponse> setProbeInterval(GetProbeInterval.Type probe, long ms);

    CompletableFuture<SetEnabledMetricsResponse> setEnabledMetrics(SetEnabledMetrics.Type type, boolean enabled);

    CompletableFuture<SetAPIIntervalsResponse> setAPIIntervals(SetAPIIntervals.TypedInterval interval, SetAPIIntervals.TypedInterval ... optional);

    CompletableFuture<GetServiceListenersResponse> getServiceListeners();
}
