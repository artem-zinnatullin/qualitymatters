package com.artemzin.qualitymatters.developer_settings;

import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.ui.other.NoOpViewModifier;
import com.artemzin.qualitymatters.ui.other.ViewModifier;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DeveloperSettingsModule {

    @NonNull
    public static final String MAIN_ACTIVITY_VIEW_MODIFIER = "main_activity_view_modifier";

    @Provides @NonNull @Named(MAIN_ACTIVITY_VIEW_MODIFIER)
    public ViewModifier provideMainActivityViewModifier() {
        return new NoOpViewModifier();
    }

    @Provides
    @NonNull
    public DeveloperSettingsModel provideDeveloperSettingsModel() {
        return () -> {
            // no-op!
        };
    }

    @Provides
    @NonNull
    @Singleton
    public LeakCanaryProxy provideLeakCanaryProxy() {
        return new NoOpLeakCanaryProxy();
    }
}
