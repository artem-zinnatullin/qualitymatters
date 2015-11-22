package com.artemzin.androiddevelopmentculture;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

// It's a Dagger module that provides application level dependencies.
@Module
public class ApplicationModule {

    @NonNull
    private final ADCApp adcApp;

    public ApplicationModule(@NonNull ADCApp adcApp) {
        this.adcApp = adcApp;
    }

    @Provides @NonNull @Singleton
    public ADCApp provideAdcApp() {
        return adcApp;
    }
}
