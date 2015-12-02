package com.artemzin.qualitymatters.developer_settings;

import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.QualityMattersApp;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.atomic.AtomicBoolean;

public class DeveloperSettingsModelImpl implements DeveloperSettingModel {

    @NonNull
    private final QualityMattersApp qualityMattersApp;

    @NonNull
    private final DeveloperSettings developerSettings;

    @NonNull
    private final OkHttpClient okHttpClient;

    @NonNull
    private AtomicBoolean stethoAlreadyEnabled = new AtomicBoolean();

    public DeveloperSettingsModelImpl(@NonNull QualityMattersApp qualityMattersApp,
                                      @NonNull DeveloperSettings developerSettings,
                                      @NonNull OkHttpClient okHttpClient) {
        this.qualityMattersApp = qualityMattersApp;
        this.developerSettings = developerSettings;
        this.okHttpClient = okHttpClient;
    }

    public boolean isStethoEnabled() {
        return developerSettings.isStethoEnabled();
    }

    public void changeStethoState(boolean enabled) {
        developerSettings.saveIsStethoEnabled(enabled);
        apply();
    }

    @Override
    public void apply() {
        // Stetho can not be enabled twice.
        if (stethoAlreadyEnabled.compareAndSet(false, true)) {
            if (isStethoEnabled()) {
                Stetho.initializeWithDefaults(qualityMattersApp);
                okHttpClient.interceptors().add(new StethoInterceptor());
            }
        }
    }
}
