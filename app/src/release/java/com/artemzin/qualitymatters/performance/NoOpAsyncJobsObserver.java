package com.artemzin.qualitymatters.performance;

import android.support.annotation.NonNull;

public class NoOpAsyncJobsObserver implements AsyncJobsObserver {

    @AnyThread
    @Override
    public void addListener(@NonNull Listener listener) {
        // no-op
    }

    @AnyThread
    @Override
    public void removeListener(@NonNull Listener listener) {
        // no-op
    }

    @AnyThread
    @Override
    public int numberOfRunningAsyncJobs() {
        // no-op
        return 0;
    }

    @AnyThread
    @NonNull
    @Override
    public AsyncJob asyncJobStarted(@NonNull String name) {
        // no-op
        return AsyncJob.create(0, name);
    }

    @AnyThread
    @Override
    public void asyncJobFinished(@NonNull AsyncJob asyncJob) {
        // no-op
    }
}
