package com.artemzin.qualitymatters.api.entities;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class ItemTest {

    @Test
    public void hashCode_equals_shouldWorkCorrectly() {
        EqualsVerifier
                .forExamples(
                        Item.builder().id("id1").imagePreviewUrl("i1").title("Title1").shortDescription("s1").build(),
                        Item.builder().id("id2").imagePreviewUrl("i2").title("Title2").shortDescription("s2").build())
                .suppress(Warning.NULL_FIELDS) // AutoValue checks nullability, EqualsVerifier does not expect that by default.
                .verify();
    }
}