package com.artemzin.qualitymatters.developer_settings;

import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

import com.artemzin.qualitymatters.QualityMattersApp;
import com.codemonkeylabs.fpslibrary.TinyDancer;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.atomic.AtomicBoolean;

import timber.log.Timber;

import static android.view.Gravity.START;
import static android.view.Gravity.TOP;

public class DeveloperSettingsModelImpl implements DeveloperSettingModel {

    @NonNull
    private final QualityMattersApp qualityMattersApp;

    @NonNull
    private final DeveloperSettings developerSettings;

    @NonNull
    private final OkHttpClient okHttpClient;

    @NonNull
    private final LeakCanaryProxy leakCanaryProxy;

    @NonNull
    private AtomicBoolean stethoAlreadyEnabled = new AtomicBoolean();

    @NonNull
    private AtomicBoolean leakCanaryAlreadyEnabled = new AtomicBoolean();

    @NonNull
    private AtomicBoolean tinyDancerDisplayed = new AtomicBoolean();

    public DeveloperSettingsModelImpl(@NonNull QualityMattersApp qualityMattersApp,
                                      @NonNull DeveloperSettings developerSettings,
                                      @NonNull OkHttpClient okHttpClient,
                                      @NonNull LeakCanaryProxy leakCanaryProxy) {
        this.qualityMattersApp = qualityMattersApp;
        this.developerSettings = developerSettings;
        this.okHttpClient = okHttpClient;
        this.leakCanaryProxy = leakCanaryProxy;
    }

    public boolean isStethoEnabled() {
        return developerSettings.isStethoEnabled();
    }

    public void changeStethoState(boolean enabled) {
        developerSettings.saveIsStethoEnabled(enabled);
        apply();
    }

    public boolean isLeakCanaryEnabled() {
        return developerSettings.isLeakCanaryEnabled();
    }

    public void changeLeakCanaryState(boolean enabled) {
        developerSettings.saveIsLeakCanaryEnabled(enabled);
        apply();
    }

    public boolean isTinyDancerEnabled() {
        return developerSettings.isTinyDancerEnabled();
    }

    public void changeTinyDancerState(boolean enabled) {
        developerSettings.saveIsTinyDancerEnabled(enabled);
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

        // LeakCanary can not be enabled twice.
        if (leakCanaryAlreadyEnabled.compareAndSet(false, true)) {
            if (isLeakCanaryEnabled()) {
                leakCanaryProxy.init();
            }
        }

        if (isTinyDancerEnabled() && tinyDancerDisplayed.compareAndSet(false, true)) {
            final DisplayMetrics displayMetrics = qualityMattersApp.getResources().getDisplayMetrics();

            TinyDancer.create()
                    .redFlagPercentage(0.2f)
                    .yellowFlagPercentage(0.05f)
                    .startingGravity(TOP | START)
                    .startingXPosition(displayMetrics.widthPixels / 10)
                    .startingYPosition(displayMetrics.heightPixels / 4)
                    .show(qualityMattersApp);
        } else if (tinyDancerDisplayed.compareAndSet(true, false)) {
            try {
                TinyDancer.hide(qualityMattersApp);
            } catch (Exception e) {
                // In some cases TinyDancer can not be hidden without exception: for example when you start it first time on Android 6.
                Timber.e(e, "Can not hide TinyDancer");
            }
        }
    }
}
