package com.artemzin.qualitymatters.performance;

import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class AsyncJobsObserverImplTest {

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private AsyncJobsObserver asyncJobsObserver;

    @Before
    public void beforeEachTest() {
        asyncJobsObserver = new AsyncJobsObserverImpl();
    }

    @Test
    public void numberOfRunningAsyncJobs_shouldBeZeroByDefault() {
        assertThat(asyncJobsObserver.numberOfRunningAsyncJobs()).isEqualTo(0);
    }

    @Test
    public void numberOfRunningAsyncJobs_shouldCorrelateWithStartAndFinish() {
        AsyncJob asyncJob = asyncJobsObserver.asyncJobStarted("test job");
        assertThat(asyncJobsObserver.numberOfRunningAsyncJobs()).isEqualTo(1);

        asyncJobsObserver.asyncJobFinished(asyncJob);
        assertThat(asyncJobsObserver.numberOfRunningAsyncJobs()).isEqualTo(0);
    }

    @Test
    public void addListener_shouldAddListenerWithoutExceptions() {
        AsyncJobsObserver.Listener listener = mock(AsyncJobsObserver.Listener.class);

        asyncJobsObserver.addListener(listener);
        verifyZeroInteractions(listener);
    }

    @Test
    public void removeListener_shouldThrowExceptionIfListenerWasNotRegistered() {
        try {
            asyncJobsObserver.removeListener(mock(AsyncJobsObserver.Listener.class));
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("Listener was not registered!");
        }
    }

    @Test
    public void removeListener_shouldNormallyRemoveKnownListener() {
        AsyncJobsObserver.Listener listener = mock(AsyncJobsObserver.Listener.class);
        asyncJobsObserver.addListener(listener);
        asyncJobsObserver.removeListener(listener); // No exceptions expected.
        verifyZeroInteractions(listener);
    }

    @Test
    public void asyncJobStarted_shouldReturnAsyncJob() {
        AsyncJob asyncJob1 = asyncJobsObserver.asyncJobStarted("test job1");
        AsyncJob asyncJob2 = asyncJobsObserver.asyncJobStarted("test job2");

        assertThat(asyncJob1.id()).isNotEqualTo(asyncJob2.id());
        assertThat(asyncJob1.name()).isEqualTo("test job1");
        assertThat(asyncJob2.name()).isEqualTo("test job2");
    }

    @Test
    public void asyncJobStarted_shouldNotifyListenersAboutChangedNumberOfAsyncJobs() {
        AsyncJobsObserver.Listener listener1 = mock(AsyncJobsObserver.Listener.class);
        AsyncJobsObserver.Listener listener2 = mock(AsyncJobsObserver.Listener.class);

        asyncJobsObserver.addListener(listener1);
        asyncJobsObserver.addListener(listener2);

        verifyZeroInteractions(listener1, listener2);

        asyncJobsObserver.asyncJobStarted("test job");
        verify(listener1).onNumberOfRunningAsyncJobsChanged(1);
        verify(listener2).onNumberOfRunningAsyncJobsChanged(1);
    }

    @Test
    public void asyncJobFinished_shouldThrowExceptionIfAsyncJobWasNotRegisteredInObserver() {
        AsyncJob asyncJob = AsyncJob.create(1, "not created by async jobs observer!");

        try {
            asyncJobsObserver.asyncJobFinished(asyncJob);
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("Async job were registered in the AsyncJobsObserver! Job: " + asyncJob);
        }
    }

    @Test
    public void asyncJobFinished_shouldNormallyRegisterFinishOfKnownAsyncJob() {
        AsyncJob asyncJob = asyncJobsObserver.asyncJobStarted("test job");
        asyncJobsObserver.asyncJobFinished(asyncJob); // No exceptions expected.
    }

    @Test
    public void asyncJobFinished_shouldNotifyListenersAboutChangedNumberOfAsyncJobs() {
        AsyncJob asyncJob = asyncJobsObserver.asyncJobStarted("test job");

        AsyncJobsObserver.Listener listener1 = mock(AsyncJobsObserver.Listener.class);
        AsyncJobsObserver.Listener listener2 = mock(AsyncJobsObserver.Listener.class);

        asyncJobsObserver.addListener(listener1);
        asyncJobsObserver.addListener(listener2);

        // We don't expect callbacks about previous changes.
        verifyZeroInteractions(listener1, listener2);

        asyncJobsObserver.asyncJobFinished(asyncJob);
        verify(listener1).onNumberOfRunningAsyncJobsChanged(0);
        verify(listener2).onNumberOfRunningAsyncJobsChanged(0);

        verifyNoMoreInteractions(listener1, listener2);
    }
}
