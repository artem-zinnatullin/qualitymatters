package com.artemzin.qualitymatters.other;

import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.performance.AsyncJob;
import com.artemzin.qualitymatters.performance.AsyncJobsObserver;

public class FinishAsyncJobSubscription extends DisposableSubscription {

    @SuppressWarnings("PMD.EmptyCatchBlock")
    public FinishAsyncJobSubscription(@NonNull AsyncJobsObserver asyncJobsObserver, @NonNull AsyncJob asyncJob) {
        super(() -> {
            try {
                asyncJobsObserver.asyncJobFinished(asyncJob);
            } catch (IllegalArgumentException possible) {
                // Do nothing, async job was probably already finished normally.
            }
        });
    }
}
