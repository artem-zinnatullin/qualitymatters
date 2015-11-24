package com.artemzin.androiddevelopmentculture.api.entities;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

// This class is immutable, it has correctly implemented hashCode and equals.
// Thanks to AutoValue https://github.com/google/auto/tree/master/value.
@AutoValue
@JsonDeserialize(builder = AutoValue_Item.Builder.class)
public abstract class Item {

    private static final String JSON_PROPERTY_ID = "id";
    private static final String JSON_PROPERTY_TITLE = "title";
    private static final String JSON_PROPERTY_SHORT_DESCRIPTION = "short_description";

    @NonNull
    public static Builder builder() {
        return new AutoValue_Item.Builder();
    }

    @NonNull
    @JsonProperty(JSON_PROPERTY_ID)
    public abstract String id();

    @NonNull
    @JsonProperty(JSON_PROPERTY_TITLE)
    public abstract String title();

    @NonNull
    @JsonProperty(JSON_PROPERTY_SHORT_DESCRIPTION)
    public abstract String shortDescription();

    @AutoValue.Builder
    public static abstract class Builder {

        @NonNull
        @JsonProperty(JSON_PROPERTY_ID)
        public abstract Builder id(@NonNull String id);

        @NonNull
        @JsonProperty(JSON_PROPERTY_TITLE)
        public abstract Builder title(@NonNull String title);

        @NonNull
        @JsonProperty(JSON_PROPERTY_SHORT_DESCRIPTION)
        public abstract Builder shortDescription(@NonNull String shortDescription);

        @NonNull
        public abstract Item build();
    }
}
