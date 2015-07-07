package com.boundary.meter.client.command;

import com.boundary.meter.client.model.ImmutableMeasure;
import com.boundary.meter.client.model.Measure;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class AddMeasuresTest {

    @Test
    public void testAddMeasures() {

        Measure m = ImmutableMeasure.of("the-measure", 44.2);
        AddMeasures am = new AddMeasures(m);

        String data = (String) am.getParams().get("data");

        validateMeasureString(m, data);
    }

    @Test
    public void testAddMeasureList() {

        Measure m0 = ImmutableMeasure.of("the-measure", 44.2);
        Measure m1 = ImmutableMeasure.of("the-other-measure", 34.2);
        AddMeasures am = new AddMeasures(ImmutableList.of(m0, m1));

        @SuppressWarnings("unchecked")
        List<String> data = (List<String>) am.getParams().get("data");

        validateMeasureString(m0, data.get(0));
        validateMeasureString(m1, data.get(1));

    }


    private void validateMeasureString(Measure m, String data) {
        assertTrue(data.startsWith("_bmetric:" + m.name()));
        assertTrue(data.contains("|v:" + m.value()));
    }
}
