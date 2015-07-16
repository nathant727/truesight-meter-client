package com.boundary.meter.client.command;

import com.boundary.meter.client.model.Measure;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class AddMeasures extends VoidCommand {

    private static final String MEASURE_FIELD_FMT = "%s:%s";
    private static final String FIELD_DELIMITER = "|";

    private static final String MEASURE_ID = "_bmetric";
    private static final String SOURCE_ID = "s";
    private static final String TIMESTAMP_ID = "t";
    private static final String VALUE_ID = "v";


    private static final Predicate<String> hasPipes = Pattern.compile("\\" + FIELD_DELIMITER).asPredicate();

    private static final  BiFunction<String, String, String> format = (id, data) -> {
        if (hasPipes.test(data)) {
            return String.format(MEASURE_FIELD_FMT, id, data.replaceAll("\\" + FIELD_DELIMITER, "\\\\" + FIELD_DELIMITER));
        } else {
            return String.format(MEASURE_FIELD_FMT, id, data);
        }
    };

    private final ImmutableMap<String, Object> params;

    public AddMeasures(ImmutableMap<String, Object> params) {
        this.params = params;
    }

    public static AddMeasures of(Measure measure) {
        return new AddMeasures(ImmutableMap.of("data", toMeasureString(measure)));
    }

    public static AddMeasures of(List<Measure> measures) {
        return new AddMeasures(ImmutableMap.of("data", toMeasureArrayString(measures)));
    }

    private static List<String> toMeasureArrayString(List<Measure> measures) {
        return measures
                .stream()
                .map(AddMeasures::toMeasureString)
                .collect(toList());
    }

    private static String toMeasureString(Measure measure) {

        final ImmutableList.Builder<String> fields = ImmutableList.builder();

        fields.add(format(MEASURE_ID, measure.name().toUpperCase()));
        fields.add(format(VALUE_ID, String.format("%f", measure.value())));

        fields.add(format(TIMESTAMP_ID, String.valueOf(measure.timestamp().toEpochMilli())));


        measure.source().ifPresent(source -> fields.add(format(SOURCE_ID, source)));
        return fields.build().stream().collect(joining(FIELD_DELIMITER));
    }

    private static String format(String fieldId, String data) {
        return format.apply(fieldId, data);
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
