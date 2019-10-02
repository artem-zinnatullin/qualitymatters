package com.artemzin.qualitymatters.items;

import com.artemzin.qualitymatters.api.QualityMattersRestApi;
import com.artemzin.qualitymatters.items.entities.Item;

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
    private QualityMattersRestApi qualityMattersRestApi;
    private ItemsModel itemsModel;

    @Before
    public void beforeEachTest() {
        qualityMattersRestApi = mock(QualityMattersRestApi.class);
        itemsModel = new ItemsModel(qualityMattersRestApi);
    }

    @Test
    public void getItems_shouldReturnItemsFromQualityMattersRestApi() {
        List<Item> items = asList(mock(Item.class), mock(Item.class));
        when(qualityMattersRestApi.items()).thenReturn(Single.just(items));

        assertThat(itemsModel.getItems().toBlocking().value()).containsExactlyElementsOf(items);
    }

    @Test
    public void getItems_shouldReturnErrorFromQualityMattersRestApi() {
        Exception error = new RuntimeException();
        when(qualityMattersRestApi.items()).thenReturn(Single.error(error));

        try {
            itemsModel.getItems().toBlocking().value();
            failBecauseExceptionWasNotThrown(RuntimeException.class);
        } catch (Exception expected) {
            assertThat(expected).isSameAs(error);
        }
    }
}