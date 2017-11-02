package com.artemzin.qualitymatters.ui.presenters;

import android.support.annotation.NonNull;
import com.google.auto.value.AutoValue;
import io.reactivex.Scheduler;


// Such approach allows us configure presenter in runtime without hardcoded values.
// Also, it allows us easily change some parts of the presenter configuration for tests.
@AutoValue
public abstract class ItemsPresenterConfiguration {

    @NonNull
    public static Builder builder() {
        return new AutoValue_ItemsPresenterConfiguration.Builder();
    }

    @NonNull
    public abstract Scheduler ioScheduler();

    @AutoValue.Builder
    public static abstract class Builder {

        @NonNull
        public abstract Builder ioScheduler(@NonNull Scheduler ioScheduler);

        @NonNull
        public abstract ItemsPresenterConfiguration build();
    }
}
