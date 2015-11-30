package com.artemzin.qualitymatters.performance;

import android.support.annotation.NonNull;

public interface AsyncJobsObserver {

    interface Listener {
        @AnyThread
        void onNoMoreAsyncJobsRunning();
    }

    @AnyThread
    void addListener(@NonNull Listener listener);

    @AnyThread
    void removeListener(@NonNull Listener listener);

    @AnyThread
    int numberOfRunningAsyncJobs();

    @AnyThread
    @NonNull
    AsyncJob asyncJobStarted();

    @AnyThread
    void asyncJobFinished(@NonNull AsyncJob asyncJob);
}
