package com.artemzin.qualitymatters.items.entities;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

// This class is immutable, it has correctly implemented hashCode and equals.
// Thanks to AutoValue https://github.com/google/auto/tree/master/value.
@AutoValue
public abstract class Item {
    private static final String JSON_PROPERTY_ID = "id";
    private static final String JSON_PROPERTY_IMAGE_PREVIEW_URL = "image_preview_url";
    private static final String JSON_PROPERTY_TITLE = "title";
    private static final String JSON_PROPERTY_SHORT_DESCRIPTION = "short_description";

    @NonNull
    public static Builder builder() {
        return new $AutoValue_Item.Builder();
    }

    @NonNull
    public static TypeAdapter<Item> typeAdapter(Gson gson) {
        return new AutoValue_Item.GsonTypeAdapter(gson);
    }

    @NonNull
    @SerializedName(JSON_PROPERTY_ID)
    public abstract String id();

    @NonNull
    @SerializedName(JSON_PROPERTY_IMAGE_PREVIEW_URL)
    public abstract String imagePreviewUrl();

    @NonNull
    @SerializedName(JSON_PROPERTY_TITLE)
    public abstract String title();

    @NonNull
    @SerializedName(JSON_PROPERTY_SHORT_DESCRIPTION)
    public abstract String shortDescription();

    @AutoValue.Builder
    public static abstract class Builder {

        @NonNull
        public abstract Builder id(@NonNull String id);

        @NonNull
        public abstract Builder imagePreviewUrl(@NonNull String imagePreviewUrl);

        @NonNull
        public abstract Builder title(@NonNull String title);

        @NonNull
        public abstract Builder shortDescription(@NonNull String shortDescription);

        @NonNull
        public abstract Item build();
    }
}
