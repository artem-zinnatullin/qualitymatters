package com.artemzin.qualitymatters.other;

import android.support.annotation.NonNull;
import com.artemzin.qualitymatters.performance.AsyncJob;
import com.artemzin.qualitymatters.performance.AsyncJobsObserver;
import io.reactivex.functions.Action;

public class FinishAsyncJobAction implements Action {

    private final AsyncJobsObserver asyncJobsObserver;
    private final AsyncJob asyncJob;

    public FinishAsyncJobAction(@NonNull AsyncJobsObserver asyncJobsObserver, @NonNull AsyncJob asyncJob) {
        this.asyncJobsObserver = asyncJobsObserver;
        this.asyncJob = asyncJob;
    }

    @Override
    public void run() throws Exception {
        asyncJobsObserver.asyncJobFinished(asyncJob);
    }
}
