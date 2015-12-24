package com.artemzin.qualitymatters.functional_tests;

import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.DaggerApplicationComponent;
import com.artemzin.qualitymatters.QualityMattersApp;
import com.artemzin.qualitymatters.models.AnalyticsModel;
import com.artemzin.qualitymatters.models.ModelsModule;

import timber.log.Timber;

public class QualityMattersFunctionalTestApp extends QualityMattersApp {

    @NonNull
    @Override
    protected DaggerApplicationComponent.Builder prepareApplicationComponent() {
        return super.prepareApplicationComponent()
                .modelsModule(new ModelsModule() {
                    @NonNull
                    @Override
                    public AnalyticsModel provideAnalyticsModel(@NonNull QualityMattersApp app) {
                        // We don't need real analytics in Functional tests, but let's just log it instead!
                        return new AnalyticsModel() {

                            // We'll check that application initializes Analytics before working with it!
                            private volatile boolean isInitialized;

                            @Override
                            public void init() {
                                isInitialized = true;
                                Timber.d("Analytics: initialized.");
                            }

                            @Override
                            public void sendEvent(@NonNull String eventName) {
                                throwIfNotInitialized();
                                Timber.d("Analytics: send event %s", eventName);
                            }

                            @Override
                            public void sendError(@NonNull String message, @NonNull Throwable error) {
                                throwIfNotInitialized();
                                Timber.e(error, message);
                            }

                            private void throwIfNotInitialized() {
                                if (!isInitialized) {
                                    throw new AssertionError("Analytics was not initialized!");
                                }
                            }
                        };
                    }
                });
    }
}
