package com.artemzin.qualitymatters.developer_settings;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

import com.artemzin.qualitymatters.BuildConfig;
import com.codemonkeylabs.fpslibrary.TinyDancer;
import com.facebook.stetho.Stetho;

import java.util.concurrent.atomic.AtomicBoolean;

import hu.supercluster.paperwork.Paperwork;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

import static android.view.Gravity.START;
import static android.view.Gravity.TOP;

public class DeveloperSettingsModelImpl implements DeveloperSettingsModel {

    @NonNull
    private final Application application;

    @NonNull
    private final DeveloperSettings developerSettings;

    @NonNull
    private final HttpLoggingInterceptor httpLoggingInterceptor;

    @NonNull
    private final LeakCanaryProxy leakCanaryProxy;

    @NonNull
    private final Paperwork paperwork;

    @NonNull
    private AtomicBoolean stethoAlreadyEnabled = new AtomicBoolean();

    @NonNull
    private AtomicBoolean leakCanaryAlreadyEnabled = new AtomicBoolean();

    @NonNull
    private AtomicBoolean tinyDancerDisplayed = new AtomicBoolean();

    public DeveloperSettingsModelImpl(@NonNull Application application,
                                      @NonNull DeveloperSettings developerSettings,
                                      @NonNull HttpLoggingInterceptor httpLoggingInterceptor,
                                      @NonNull LeakCanaryProxy leakCanaryProxy,
                                      @NonNull Paperwork paperwork) {
        this.application = application;
        this.developerSettings = developerSettings;
        this.httpLoggingInterceptor = httpLoggingInterceptor;
        this.leakCanaryProxy = leakCanaryProxy;
        this.paperwork = paperwork;
    }

    @NonNull
    public String getGitSha() {
        return paperwork.get("gitSha");
    }

    @NonNull
    public String getBuildDate() {
        return paperwork.get("buildDate");
    }

    @NonNull
    public String getBuildVersionCode() {
        return String.valueOf(BuildConfig.VERSION_CODE);
    }

    @NonNull
    public String getBuildVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    public boolean isStethoEnabled() {
        return developerSettings.isStethoEnabled();
    }

    public boolean hasStethoStateChanged(boolean enabled) {
        return isStethoEnabled() != enabled;
    }

    public void changeStethoState(boolean enabled) {
        developerSettings.saveIsStethoEnabled(enabled);
        apply();
    }

    public boolean isLeakCanaryEnabled() {
        return developerSettings.isLeakCanaryEnabled();
    }

    public boolean hasLeakCanaryStateChanged(boolean enabled) {
        return isLeakCanaryEnabled() != enabled;
    }

    public void changeLeakCanaryState(boolean enabled) {
        developerSettings.saveIsLeakCanaryEnabled(enabled);
        apply();
    }

    public boolean isTinyDancerEnabled() {
        return developerSettings.isTinyDancerEnabled();
    }

    public boolean hasTinyDancerStateChanged(boolean enabled) {
        return isTinyDancerEnabled() != enabled;
    }

    public void changeTinyDancerState(boolean enabled) {
        developerSettings.saveIsTinyDancerEnabled(enabled);
        apply();
    }

    @NonNull
    public HttpLoggingInterceptor.Level getHttpLoggingLevel() {
        return developerSettings.getHttpLoggingLevel();
    }

    public boolean hasHttpLoggingLevelChanged(@NonNull HttpLoggingInterceptor.Level loggingLevel) {
        return getHttpLoggingLevel() != loggingLevel;
    }

    public void changeHttpLoggingLevel(@NonNull HttpLoggingInterceptor.Level loggingLevel) {
        developerSettings.saveHttpLoggingLevel(loggingLevel);
        apply();
    }

    @Override
    public void apply() {
        // Stetho can not be enabled twice.
        if (stethoAlreadyEnabled.compareAndSet(false, true)) {
            if (isStethoEnabled()) {
                Stetho.initializeWithDefaults(application);
            }
        }

        // LeakCanary can not be enabled twice.
        if (leakCanaryAlreadyEnabled.compareAndSet(false, true)) {
            if (isLeakCanaryEnabled()) {
                leakCanaryProxy.init();
            }
        }

        if (isTinyDancerEnabled() && tinyDancerDisplayed.compareAndSet(false, true)) {
            final DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();

            TinyDancer.create()
                    .redFlagPercentage(0.2f)
                    .yellowFlagPercentage(0.05f)
                    .startingGravity(TOP | START)
                    .startingXPosition(displayMetrics.widthPixels / 10)
                    .startingYPosition(displayMetrics.heightPixels / 4)
                    .show(application);
        } else if (tinyDancerDisplayed.compareAndSet(true, false)) {
            try {
                TinyDancer.hide(application);
            } catch (Exception e) {
                // In some cases TinyDancer can not be hidden without exception: for example when you start it first time on Android 6.
                Timber.e(e, "Can not hide TinyDancer");
            }
        }

        httpLoggingInterceptor.setLevel(developerSettings.getHttpLoggingLevel());
    }
}
