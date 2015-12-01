package com.artemzin.qualitymatters.performance;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AsyncJobsModule {

    @Provides @NonNull @Singleton
    public AsyncJobsObserver provideAsyncJobsObserver() {
        return new AsyncJobsObserverImpl();
    }
}
