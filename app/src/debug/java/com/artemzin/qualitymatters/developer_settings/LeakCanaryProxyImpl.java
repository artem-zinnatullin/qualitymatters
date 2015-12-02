package com.artemzin.qualitymatters.developer_settings;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artemzin.qualitymatters.QualityMattersApp;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class LeakCanaryProxyImpl implements LeakCanaryProxy {

    @NonNull
    private final QualityMattersApp qualityMattersApp;

    @Nullable
    private RefWatcher refWatcher;

    public LeakCanaryProxyImpl(@NonNull QualityMattersApp qualityMattersApp) {
        this.qualityMattersApp = qualityMattersApp;
    }

    @Override
    public void init() {
        refWatcher = LeakCanary.install(qualityMattersApp);
    }

    @Override
    public void watch(@NonNull Object object) {
        if (refWatcher != null) {
            refWatcher.watch(object);
        }
    }
}
