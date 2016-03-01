package com.artemzin.qualitymatters;

import android.app.Application;
import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.models.AnalyticsModel;
import com.artemzin.qualitymatters.models.ModelsModule;

import static org.mockito.Mockito.mock;

public class QualityMattersUnitTestApp extends QualityMattersApp {

    @NonNull
    @Override
    protected DaggerApplicationComponent.Builder prepareApplicationComponent() {
        return super.prepareApplicationComponent()
                .modelsModule(new ModelsModule() {
                    @NonNull
                    @Override
                    public AnalyticsModel provideAnalyticsModel(@NonNull Application app) {
                        return mock(AnalyticsModel.class); // We don't need real analytics in Unit tests.
                    }
                });
    }
}
