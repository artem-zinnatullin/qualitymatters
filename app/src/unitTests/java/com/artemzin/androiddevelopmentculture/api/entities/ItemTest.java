package com.artemzin.androiddevelopmentculture.api.entities;

import com.artemzin.androiddevelopmentculture.ADCRobolectricTestRunner;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(ADCRobolectricTestRunner.class)
public class ItemTest {

    // Why test JSON serialization/deserialization?
    // 1. Update JSON libraries without worrying about breaking changes.
    // 2. Be sure that @JsonIgnore and similar annotations do not affect expected behavior (cc @karlicos).
    @Test
    public void fromJson() throws IOException {
        ObjectMapper objectMapper = ADCRobolectricTestRunner.adcApp().applicationComponent().objectMapper();

        Item item = objectMapper.readValue("{ " +
                        "\"id\": \"test_id\", " +
                        "\"title\": \"Test title\", " +
                        "\"short_description\": \"Test short description\"" +
                        "}",
                Item.class);

        assertThat(item.id()).isEqualTo("test_id");
        assertThat(item.title()).isEqualTo("Test title");
        assertThat(item.shortDescription()).isEqualTo("Test short description");
    }

    @Test
    public void hashCode_equals_shouldWorkCorrectly() {
        EqualsVerifier
                .forExamples(
                        Item.builder().id("id1").title("Title1").shortDescription("s1").build(),
                        Item.builder().id("id2").title("Title2").shortDescription("s2").build())
                .suppress(Warning.NULL_FIELDS) // AutoValue checks nullability, EqualsVerifier does not expect that by default.
                .verify();
    }
}