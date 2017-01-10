package com.artemzin.qualitymatters.ui.views;

import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.performance.AnyThread;
import okhttp3.logging.HttpLoggingInterceptor;

public interface DeveloperSettingsView {

    @AnyThread
    void changeGitSha(@NonNull String gitSha);

    @AnyThread
    void changeBuildDate(@NonNull String date);

    @AnyThread
    void changeBuildVersionCode(@NonNull String versionCode);

    @AnyThread
    void changeBuildVersionName(@NonNull String versionName);

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
