package com.boundary.meter.client.command;

import com.boundary.meter.client.Json;
import com.boundary.meter.client.model.Measure;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkState;

public class AddMeasures extends VoidCommand {

    /*'
    {"jsonrpc":"2.0","method":"metric","params":{"data":["_bmetric:CPU_1|v:1.20", "_bmetric:CPU_2|v:1.50"]}}
{"jsonrpc":"2.0","method":"metric","params":{"data":"_bmetric:CPU_1|v:1.20"}}
     */
    private static final String MEASURE_DATA_FORMAT = "_bmetric:%s|v:%f";

    private final int id;
    private final Map<String, Object> params;

    public AddMeasures(Measure measure) {
        // todo arrays
        this(ImmutableMap.of("data", toMeasureString(measure)));
    }

    public AddMeasures(Map<String, Object> params) {
        this(-1, params);
    }

    public AddMeasures(int id, Map<String, Object> params) {
        this.id = id;
        this.params = params;
    }

    public AddMeasures(List<Measure> measures) {
        this(ImmutableMap.of("data", toMeasureArrayString(measures)));
    }



    private  static String toMeasureString(Measure measure) {
        return String.format(MEASURE_DATA_FORMAT, measure.getName(), measure.getValue());
    }

    private static List<String> toMeasureArrayString(List<Measure> measures) {
        return measures
                .stream()
                .map(AddMeasures::toMeasureString)
                .collect(Collectors.toList());
    }

    @Override
    public VoidResponse convertResponse(int id, JsonNode node) {
        throw new IllegalArgumentException("should not happen");
    }

    @Override
    public Command<VoidResponse> withId(int id) {
        return new AddMeasures(id, getParams() );
    }

    @Override
    public String getMethod() {
        return "metric";
    }

    @Override
    public int getId() {
        checkState(id >= 0, "Invalid/uninitialized id");
        return id;
    }

    @Override
    public Map<String, Object> getParams() {
        return this.params;
    }
}
