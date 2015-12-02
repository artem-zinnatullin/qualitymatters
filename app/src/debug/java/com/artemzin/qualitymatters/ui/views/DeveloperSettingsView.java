package com.artemzin.qualitymatters.ui.views;

import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.performance.AnyThread;

public interface DeveloperSettingsView {

    @AnyThread
    void changeStethoState(boolean enabled);

    @AnyThread
    void changeLeakCanaryState(boolean enabled);

    @AnyThread
    void showMessage(@NonNull String message);

    @AnyThread
    void showAppNeedsToBeRestarted();
}
