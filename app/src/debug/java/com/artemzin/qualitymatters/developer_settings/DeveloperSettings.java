package com.artemzin.qualitymatters.developer_settings;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import timber.log.Timber;

/**
 * Wrapper over {@link android.content.SharedPreferences} to store developer settings.
 */
public class DeveloperSettings {

    private static final String KEY_IS_STETHO_ENABLED = "is_stetho_enabled";
    private static final String KEY_IS_LEAK_CANARY_ENABLED = "is_leak_canary_enabled";
    private static final String KEY_IS_TINY_DANCER_ENABLED = "is_tiny_dancer_enabled";
    private static final String KEY_HTTP_LOGGING_LEVEL = "key_http_logging_level";

    @NonNull
    private final SharedPreferences sharedPreferences;

    public DeveloperSettings(@NonNull SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public boolean isStethoEnabled() {
        return sharedPreferences.getBoolean(KEY_IS_STETHO_ENABLED, false);
    }

    public void saveIsStethoEnabled(boolean isStethoEnabled) {
        sharedPreferences.edit().putBoolean(KEY_IS_STETHO_ENABLED, isStethoEnabled).apply();
    }

    public boolean isLeakCanaryEnabled() {
        return sharedPreferences.getBoolean(KEY_IS_LEAK_CANARY_ENABLED, false);
    }

    public void saveIsLeakCanaryEnabled(boolean isLeakCanaryEnabled) {
        sharedPreferences.edit().putBoolean(KEY_IS_LEAK_CANARY_ENABLED, isLeakCanaryEnabled).apply();
    }

    public boolean isTinyDancerEnabled() {
        return sharedPreferences.getBoolean(KEY_IS_TINY_DANCER_ENABLED, false);
    }

    public void saveIsTinyDancerEnabled(boolean isTinyDancerEnabled) {
        sharedPreferences.edit().putBoolean(KEY_IS_TINY_DANCER_ENABLED, isTinyDancerEnabled).apply();
    }

    @NonNull
    public HttpLoggingInterceptor.Level getHttpLoggingLevel() {
        final String savedHttpLoggingLevel = sharedPreferences.getString(KEY_HTTP_LOGGING_LEVEL, null);

        try {
            if (savedHttpLoggingLevel != null) {
                return HttpLoggingInterceptor.Level.valueOf(savedHttpLoggingLevel);
            }
        } catch (IllegalArgumentException noSuchLoggingLevel) {
            // After OkHttp update old logging level may be removed/renamed so we should handle such case.
            Timber.w("No such Http logging level in current version of the app. Saved loggingLevel = %s", savedHttpLoggingLevel);
        }

        return HttpLoggingInterceptor.Level.BASIC;
    }

    public void saveHttpLoggingLevel(@NonNull HttpLoggingInterceptor.Level loggingLevel) {
        sharedPreferences.edit().putString(KEY_HTTP_LOGGING_LEVEL, loggingLevel.toString()).apply();
    }
}
