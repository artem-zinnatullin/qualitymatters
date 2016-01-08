package com.artemzin.qualitymatters;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.api.ApiModule;
import com.artemzin.qualitymatters.developer_settings.DeveloperSettingsModel;
import com.artemzin.qualitymatters.models.AnalyticsModel;

import javax.inject.Inject;

import dagger.Lazy;
import timber.log.Timber;

public class QualityMattersApp extends Application {

    @SuppressWarnings("NullableProblems")
    // Initialized in onCreate. But be careful if you have ContentProviders
    // -> their onCreate may be called before app.onCreate()
    // -> move initialization to attachBaseContext().
    @NonNull
    private ApplicationComponent applicationComponent;

    @SuppressWarnings("NullableProblems") // Initialize in onCreate.
    @Inject
    @NonNull
    AnalyticsModel analyticsModel;

    @SuppressWarnings("NullableProblems") // Initialize in onCreate.
    @Inject
    @NonNull
    Lazy<DeveloperSettingsModel> developerSettingModel;

    // Prevent need in a singleton (global) reference to the application object.
    @NonNull
    public static QualityMattersApp get(@NonNull Context context) {
        return (QualityMattersApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = prepareApplicationComponent().build();
        applicationComponent.inject(this);

        analyticsModel.init();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            developerSettingModel.get().apply();
        }
    }

    @NonNull
    protected DaggerApplicationComponent.Builder prepareApplicationComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                // This url may be changed dynamically for tests! See ChangeableBaseUrl.
                .apiModule(new ApiModule("https://raw.githubusercontent.com/artem-zinnatullin/qualitymatters/master/rest_api/"));
    }

    @NonNull
    public ApplicationComponent applicationComponent() {
        return applicationComponent;
    }
}
