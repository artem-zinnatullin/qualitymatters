package com.artemzin.qualitymatters.integration_tests.api.other;

import com.artemzin.qualitymatters.QualityMattersIntegrationRobolectricTestRunner;
import com.artemzin.qualitymatters.api.entities.Item;
import com.artemzin.qualitymatters.other.GsonTypeAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(QualityMattersIntegrationRobolectricTestRunner.class)
public class GsonTypeAdapterFactoryTest {

    // check if factory is aware of Item type and returns a TypeAdapter
    @Test
    public void supportsItemTypeAdapter() {
        GsonTypeAdapterFactory typeAdapterFactory = QualityMattersIntegrationRobolectricTestRunner.qualityMattersApp().applicationComponent().gsonTypeAdapterFactory();

        Gson gson = new Gson();
        TypeToken<Item> itemTypeToken = new TypeToken<Item>() { };

        TypeAdapter<Item> typeAdapter = typeAdapterFactory.create(gson, itemTypeToken);

        assertThat(typeAdapter).isNotNull();
    }
}
