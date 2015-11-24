package com.artemzin.androiddevelopmentculture.models;

import android.support.annotation.NonNull;

import com.artemzin.androiddevelopmentculture.api.ADCApi;
import com.artemzin.androiddevelopmentculture.api.entities.Item;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import rx.Single;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemsModelTest {

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private ADCApi adcApi;

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private ItemsModel itemsModel;

    @Before
    public void beforeEachTest() {
        adcApi = mock(ADCApi.class);
        itemsModel = new ItemsModel(adcApi);
    }

    @Test
    public void getItems_shouldReturnItemsFromADCApi() {
        List<Item> items = asList(
                Item.builder().id("1").title("Item 1").shortDescription("s1").build(),
                Item.builder().id("2").title("Item 2").shortDescription("s2").build()
        );
        when(adcApi.items()).thenReturn(Single.just(items));

        assertThat(itemsModel.getItems().toBlocking().value()).containsExactlyElementsOf(items);
    }

    @Test
    public void getItems_shouldReturnErrorFromADCApi() {
        Exception error = new RuntimeException();
        when(adcApi.items()).thenReturn(Single.error(error));

        try {
            itemsModel.getItems().toBlocking().value();
            failBecauseExceptionWasNotThrown(RuntimeException.class);
        } catch (Exception expected) {
            assertThat(expected).isSameAs(error);
        }
    }
}