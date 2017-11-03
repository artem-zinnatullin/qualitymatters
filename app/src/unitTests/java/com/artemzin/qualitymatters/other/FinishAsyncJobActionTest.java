package com.artemzin.qualitymatters.other;

import com.artemzin.qualitymatters.performance.AsyncJob;
import com.artemzin.qualitymatters.performance.AsyncJobsObserver;

import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class FinishAsyncJobActionTest {

    @Test
    public void constructor_shouldCreateSuchActionThatWillUnregisterAsyncJob() {
        AsyncJobsObserver asyncJobsObserver = mock(AsyncJobsObserver.class);
        AsyncJob asyncJob = mock(AsyncJob.class);

        Disposable disposable = Disposables.fromAction(new FinishAsyncJobAction(asyncJobsObserver, asyncJob));
        verifyZeroInteractions(asyncJobsObserver, asyncJob);

        disposable.dispose();
        verify(asyncJobsObserver).asyncJobFinished(asyncJob);
    }
}