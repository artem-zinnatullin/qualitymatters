package com.artemzin.qualitymatters.ui.views;

import com.artemzin.qualitymatters.performance.AnyThread;

public interface DeveloperSettingsView {

    @AnyThread
    void changeStethoState(boolean enabled);

    @AnyThread
    void showAppNeedsToBeRestarted();
}
