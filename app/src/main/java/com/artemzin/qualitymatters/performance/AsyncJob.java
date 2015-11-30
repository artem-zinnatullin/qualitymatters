package com.artemzin.qualitymatters.performance;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class AsyncJob {

    @NonNull
    public static AsyncJob create(int id) {
        return new AutoValue_AsyncJob(id);
    }

    public abstract int id();
}
