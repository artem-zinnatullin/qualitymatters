package com.artemzin.qualitymatters.performance;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class AsyncJobTest {

    @Test
    public void hashCode_equals_shouldWorkCorrectly() {
        EqualsVerifier
                .forExamples(AsyncJob.create(1, "name1"), AsyncJob.create(2, "name2"))
                .suppress(Warning.NULL_FIELDS) // AutoValue checks nullability, EqualsVerifier does not expect that by default.
                .verify();
    }
}
