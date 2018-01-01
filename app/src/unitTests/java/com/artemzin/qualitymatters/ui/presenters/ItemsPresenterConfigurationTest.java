package com.artemzin.qualitymatters.ui.presenters;

import io.reactivex.schedulers.Schedulers;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class ItemsPresenterConfigurationTest {

    @Test
    public void hashCode_equals_shouldWorkCorrectly() {
        EqualsVerifier
                .forExamples(
                        ItemsPresenterConfiguration.builder().ioScheduler(Schedulers.trampoline()).build(),
                        ItemsPresenterConfiguration.builder().ioScheduler(Schedulers.io()).build())
                .suppress(Warning.NULL_FIELDS) // AutoValue checks for nulls, but EqualsVerifier does not expect that by default.
                .verify();
    }
}