package com.artemzin.qualitymatters.developer_settings;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Wrapper over {@link android.content.SharedPreferences} to store developer settings.
 */
public class DeveloperSettings {

    private static final String KEY_IS_STETHO_ENABLED = "is_stetho_enabled";

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
}
