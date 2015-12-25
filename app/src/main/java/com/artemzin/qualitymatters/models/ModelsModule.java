package com.artemzin.qualitymatters.models;

import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.QualityMattersApp;
import com.artemzin.qualitymatters.api.QualityMattersRestApi;
import com.yandex.metrica.YandexMetrica;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ModelsModule {

    @Provides @NonNull @Singleton
    public AnalyticsModel provideAnalyticsModel(@NonNull QualityMattersApp app) {
        return new YandexAppMetricaAnalytics(app);
    }

    @Provides @NonNull
    public ItemsModel provideItemsModel(@NonNull QualityMattersRestApi qualityMattersRestApi) {
        return new ItemsModel(qualityMattersRestApi);
    }

    static class YandexAppMetricaAnalytics implements AnalyticsModel {

        @NonNull
        private final QualityMattersApp app;

        YandexAppMetricaAnalytics(@NonNull QualityMattersApp app) {
            this.app = app;
        }

        @Override
        public void init() {
            YandexMetrica.activate(app, "afbc9acd-7955-4df3-a059-c486e38664e8");
            YandexMetrica.enableActivityAutoTracking(app);
        }

        @Override
        public void sendEvent(@NonNull String eventName) {
            YandexMetrica.reportEvent(eventName);
        }

        @Override
        public void sendError(@NonNull String message, @NonNull Throwable error) {
            YandexMetrica.reportError(message, error);
        }

    }
}
