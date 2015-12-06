package com.artemzin.qualitymatters.ui.views;

import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.performance.AnyThread;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

public interface DeveloperSettingsView {

    @AnyThread
    void changeStethoState(boolean enabled);

    @AnyThread
    void changeLeakCanaryState(boolean enabled);

    @AnyThread
    void changeTinyDancerState(boolean enabled);

    @AnyThread
    void changeHttpLoggingLevel(@NonNull HttpLoggingInterceptor.Level loggingLevel);

    @AnyThread
    void showMessage(@NonNull String message);

    @AnyThread
    void showAppNeedsToBeRestarted();
}
