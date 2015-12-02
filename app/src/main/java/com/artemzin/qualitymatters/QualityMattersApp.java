package com.artemzin.qualitymatters;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.api.ApiModule;
import com.artemzin.qualitymatters.developer_settings.DeveloperSettingModel;

import javax.inject.Inject;

import dagger.Lazy;

public class QualityMattersApp extends Application {

    @SuppressWarnings("NullableProblems")
    // Initialized in onCreate. But be careful if you have ContentProviders in different processes -> their onCreate will be called before app.onCreate().
    @NonNull
    private ApplicationComponent applicationComponent;

    @Inject
    Lazy<DeveloperSettingModel> developerSettingModel;

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
                // This url may be changed dynamically for tests! See ChangeableBaseUrl.
                .apiModule(new ApiModule("https://raw.githubusercontent.com/artem-zinnatullin/qualitymatters/master/rest_api/"))
                .build();

        applicationComponent.inject(this);

        if (BuildConfig.DEBUG) {
            developerSettingModel.get().apply();
        }
    }

    @NonNull
    public ApplicationComponent applicationComponent() {
        return applicationComponent;
    }
}
