package com.boundary.meter.client.command;

import com.boundary.meter.client.model.Measure;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AddMeasures extends VoidCommand {

    private static final String MEASURE_DATA_FORMAT = "_bmetric:%s|v:%f";

    private final Map<String, Object> params;

    public AddMeasures(Measure measure) {
        this(ImmutableMap.of("data", toMeasureString(measure)));
    }

    public AddMeasures(Map<String, Object> params) {
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
    public String getMethod() {
        return "metric";
    }

    @Override
    public Map<String, Object> getParams() {
        return this.params;
    }
}
