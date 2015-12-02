package com.artemzin.qualitymatters.developer_settings;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Wrapper over {@link android.content.SharedPreferences} to store developer settings.
 */
public class DeveloperSettings {

    private static final String KEY_IS_STETHO_ENABLED = "is_stetho_enabled";
    private static final String KEY_IS_LEAK_CANARY_ENABLED = "is_leak_canary_enabled";
    private static final String KEY_IS_TINY_DANCER_ENABLED = "is_tiny_dancer_enabled";

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
}
