package com.artemzin.qualitymatters.models;

import android.support.annotation.NonNull;

/**
 * Common interface for Analytics systems like: Yandex App Metrics, Google Analytics, Flurry, etc.
 */
public interface AnalyticsModel {

    void init();
    void sendEvent(@NonNull String eventName);
    void sendError(@NonNull String message, @NonNull Throwable error);
}
