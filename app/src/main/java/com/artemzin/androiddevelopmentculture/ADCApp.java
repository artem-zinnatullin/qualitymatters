package com.artemzin.androiddevelopmentculture;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

public class ADCApp extends Application {

    @SuppressWarnings("NullableProblems") // Initialized in onCreate. But be careful if you have ContentProviders.
    @NonNull
    private ApplicationComponent applicationComponent;

    // Prevent need in a singleton (global) reference to the application object.
    @NonNull
    public static ADCApp get(@NonNull Context context) {
        return (ADCApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    @NonNull
    public ApplicationComponent applicationComponent() {
        return applicationComponent;
    }
}
