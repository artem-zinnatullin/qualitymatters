package com.artemzin.qualitymatters;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.api.ApiModule;

public class QualityMattersApp extends Application {

    @SuppressWarnings("NullableProblems") // Initialized in onCreate. But be careful if you have ContentProviders.
    @NonNull
    private ApplicationComponent applicationComponent;

    // Prevent need in a singleton (global) reference to the application object.
    @NonNull
    public static QualityMattersApp get(@NonNull Context context) {
        return (QualityMattersApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .apiModule(new ApiModule("https://raw.githubusercontent.com/artem-zinnatullin/qualitymatters/master/rest_api/"))
                .build();
    }

    @NonNull
    public ApplicationComponent applicationComponent() {
        return applicationComponent;
    }
}
