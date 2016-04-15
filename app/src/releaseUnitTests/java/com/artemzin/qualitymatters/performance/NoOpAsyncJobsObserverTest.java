package com.artemzin.qualitymatters.performance;

import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

public class NoOpAsyncJobsObserverTest {
    private AsyncJobsObserver asyncJobsObserver;

    @Before
    public void beforeEachTest() {
        asyncJobsObserver = new NoOpAsyncJobsObserver();
    }

    @Test
    public void addListener_shouldNoOp() {
        AsyncJobsObserver.Listener listener = mock(AsyncJobsObserver.Listener.class);

        asyncJobsObserver.addListener(listener);
        verifyZeroInteractions(listener);
    }

    @Test
    public void removeListener_shouldNoOp() {
        AsyncJobsObserver.Listener listener = mock(AsyncJobsObserver.Listener.class);

        asyncJobsObserver.removeListener(listener);
        verifyZeroInteractions(listener);
    }

    @Test
    public void numberOfRunningAsyncJobs_shouldBeZeroByDefault() {
        assertThat(asyncJobsObserver.numberOfRunningAsyncJobs()).isEqualTo(0);
    }

    @Test
    public void numberOfRunningAsyncJobs_shouldBeZeroEvenIfSomethingRunning() {
        AsyncJob asyncJob = asyncJobsObserver.asyncJobStarted("test job");
        assertThat(asyncJobsObserver.numberOfRunningAsyncJobs()).isEqualTo(0);

        asyncJobsObserver.asyncJobFinished(asyncJob);
        assertThat(asyncJobsObserver.numberOfRunningAsyncJobs()).isEqualTo(0);
    }

    @Test
    public void asyncJobStarted_shouldReturnSomeAsyncJob() {
        assertThat(asyncJobsObserver.asyncJobStarted("test job")).isNotNull();
    }

    @Test
    public void asyncJobStarted_shouldNotNotifyListeners() {
        AsyncJobsObserver.Listener listener = mock(AsyncJobsObserver.Listener.class);
        asyncJobsObserver.addListener(listener);

        asyncJobsObserver.asyncJobStarted("test job");
        verifyZeroInteractions(listener);
    }

    @Test
    public void asyncJobFinished_shouldNotComplaintAboutUnknownAsyncJob() {
        AsyncJobsObserver.Listener listener = mock(AsyncJobsObserver.Listener.class);

        asyncJobsObserver.removeListener(listener); // No exceptions expected.
        verifyZeroInteractions(listener);
    }

    @Test
    public void asyncJobFinished_shouldNoOpForKnownListener() {
        AsyncJobsObserver.Listener listener = mock(AsyncJobsObserver.Listener.class);

        asyncJobsObserver.addListener(listener);
        asyncJobsObserver.removeListener(listener);
        verifyZeroInteractions(listener);
    }

    @Test
    public void asyncJobFinished_shouldNotNotifyListeners() {
        AsyncJobsObserver.Listener listener = mock(AsyncJobsObserver.Listener.class);
        asyncJobsObserver.addListener(listener);

        AsyncJob asyncJob = asyncJobsObserver.asyncJobStarted("test job");
        asyncJobsObserver.asyncJobFinished(asyncJob);

        verifyZeroInteractions(listener);
    }
}