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


    // see https://docs.google.com/document/d/1tRRaenDy8oImbDqfOgP9Wb7oHxUjDymb-EYqCImlMdo
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

    public AddMeasures(Measure measure) {
        this(ImmutableMap.of("data", toMeasureString(measure)));
    }

    public AddMeasures(ImmutableMap<String, Object> params) {
        this.params = params;
    }



    public AddMeasures(List<Measure> measures) {
        this(ImmutableMap.of("data", toMeasureArrayString(measures)));
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

        // TODO: hacking the ms time to seconds for the moment as the meter doesn't
        // yet handle millisecond resolution. Need to come back and fix this once the meter
        // is updated
        fields.add(format(TIMESTAMP_ID, String.valueOf(measure.timestamp().getEpochSecond())));


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
