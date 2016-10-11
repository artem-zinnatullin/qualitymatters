package com.artemzin.qualitymatters.other;

import com.artemzin.qualitymatters.api.entities.Item;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class GsonTypeAdapterFactory implements TypeAdapterFactory {
    @Override @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (Item.class.equals(type.getType())) {
            return (TypeAdapter<T>) Item.typeAdapter(gson);
        }

        return null;
    }
}
