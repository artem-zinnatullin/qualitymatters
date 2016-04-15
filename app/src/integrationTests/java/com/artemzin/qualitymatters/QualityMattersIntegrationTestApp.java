package com.artemzin.qualitymatters;

import android.app.Application;
import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.developer_settings.DevMetricsProxy;
import com.artemzin.qualitymatters.developer_settings.DeveloperSettingsModule;
import com.artemzin.qualitymatters.models.AnalyticsModel;
import com.artemzin.qualitymatters.models.ModelsModule;

import static org.mockito.Mockito.mock;

public class QualityMattersIntegrationTestApp extends QualityMattersApp {

    @NonNull
    @Override
    protected DaggerApplicationComponent.Builder prepareApplicationComponent() {
        return super.prepareApplicationComponent()
                .modelsModule(new ModelsModule() {
                    @NonNull
                    @Override
                    public AnalyticsModel provideAnalyticsModel(@NonNull Application app) {
                        return mock(AnalyticsModel.class); // We don't need real analytics in integration tests.
                    }
                })
                .developerSettingsModule(new DeveloperSettingsModule() {
                    @NonNull
                    public DevMetricsProxy provideDevMetricsProxy(@NonNull Application application) {
                        return () -> {
                            //No Op
                        };
                    }
                });
    }
}
