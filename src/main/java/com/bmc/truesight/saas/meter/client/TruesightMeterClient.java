package com.bmc.truesight.saas.meter.client;

import com.bmc.truesight.saas.meter.client.command.GetProcessInfo;
import com.bmc.truesight.saas.meter.client.command.GetProcessTopK;
import com.bmc.truesight.saas.meter.client.response.DebugResponse;
import com.bmc.truesight.saas.meter.client.response.DiscoveryResponse;
import com.bmc.truesight.saas.meter.client.response.GetProcessInfoResponse;
import com.bmc.truesight.saas.meter.client.response.GetProcessTopKResponse;
import com.bmc.truesight.saas.meter.client.response.GetServiceListenersResponse;
import com.bmc.truesight.saas.meter.client.response.GetSystemInfoResponse;
import com.bmc.truesight.saas.meter.client.response.QueryMetricResponse;
import com.bmc.truesight.saas.meter.client.response.VoidResponse;
import com.bmc.truesight.saas.meter.client.model.Event;
import com.bmc.truesight.saas.meter.client.model.Measure;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface TruesightMeterClient extends AutoCloseable {

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

    CompletableFuture<GetServiceListenersResponse> getServiceListeners();
}
