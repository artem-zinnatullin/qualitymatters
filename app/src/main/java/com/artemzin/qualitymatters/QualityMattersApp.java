package com.artemzin.qualitymatters;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.api.ApiModule;
import com.artemzin.qualitymatters.developer_settings.DevMetricsProxy;
import com.artemzin.qualitymatters.developer_settings.DeveloperSettingsModel;
import com.artemzin.qualitymatters.models.AnalyticsModel;

import timber.log.Timber;

public class QualityMattersApp extends Application {
    private ApplicationComponent applicationComponent;

    // Prevent need in a singleton (global) reference to the application object.
    @NonNull
    public static QualityMattersApp get(@NonNull Context context) {
        return (QualityMattersApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = prepareApplicationComponent().build();

        AnalyticsModel analyticsModel = applicationComponent.analyticsModel();

        analyticsModel.init();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());

            DeveloperSettingsModel developerSettingModel = applicationComponent.developerSettingModel();
            developerSettingModel.apply();

            DevMetricsProxy devMetricsProxy = applicationComponent.devMetricsProxy();
            devMetricsProxy.apply();
        }
    }

    @NonNull
    protected DaggerApplicationComponent.Builder prepareApplicationComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                // This url may be changed dynamically for tests! See HostSelectionInterceptor.
                .apiModule(new ApiModule("https://raw.githubusercontent.com/artem-zinnatullin/qualitymatters/master/rest_api/"));
    }

    @NonNull
    public ApplicationComponent applicationComponent() {
        return applicationComponent;
    }
}
