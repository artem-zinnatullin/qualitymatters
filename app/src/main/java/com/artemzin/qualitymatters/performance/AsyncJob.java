package com.artemzin.qualitymatters.performance;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class AsyncJob {

    @NonNull
    public static AsyncJob create(int id, @NonNull String name) {
        return new AutoValue_AsyncJob(id, name);
    }

    public abstract int id();

    @NonNull
    public abstract String name();
}
