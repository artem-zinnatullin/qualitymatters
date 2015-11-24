package com.artemzin.qualitymatters.ui.presenters;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import rx.schedulers.Schedulers;

public class ItemsPresenterConfigurationTest {

    @Test
    public void hashCode_equals_shouldWorkCorrectly() {
        EqualsVerifier
                .forExamples(
                        ItemsPresenterConfiguration.builder().ioScheduler(Schedulers.immediate()).build(),
                        ItemsPresenterConfiguration.builder().ioScheduler(Schedulers.io()).build())
                .suppress(Warning.NULL_FIELDS) // AutoValue checks for nulls, but EqualsVerifier does not expect that by default.
                .verify();
    }
}