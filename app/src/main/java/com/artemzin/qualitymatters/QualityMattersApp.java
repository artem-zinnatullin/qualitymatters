package com.artemzin.qualitymatters;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.api.ApiModule;
import com.artemzin.qualitymatters.developer_settings.DevMetricsProxy;
import com.artemzin.qualitymatters.developer_settings.DeveloperSettingsModel;
import com.artemzin.qualitymatters.models.AnalyticsModel;

import javax.inject.Inject;

import dagger.Lazy;
import timber.log.Timber;

public class QualityMattersApp extends Application {
    private ApplicationComponent applicationComponent;

    private AnalyticsModel analyticsModel;

    private Lazy<DeveloperSettingsModel> developerSettingModel;

    private Lazy<DevMetricsProxy> devMetricsProxy;

    // Prevent need in a singleton (global) reference to the application object.
    @NonNull
    public static QualityMattersApp get(@NonNull Context context) {
        return (QualityMattersApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = prepareApplicationComponent().build();

        analyticsModel = applicationComponent.analyticsModel();
        developerSettingModel = applicationComponent.developerSettingModel();
        devMetricsProxy = applicationComponent.devMetricsProxy();

        analyticsModel.init();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            developerSettingModel.get().apply();
            devMetricsProxy.get().apply();
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
