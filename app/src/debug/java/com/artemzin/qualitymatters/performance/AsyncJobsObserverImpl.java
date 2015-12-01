package com.artemzin.qualitymatters.performance;

import android.support.annotation.NonNull;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Collections.newSetFromMap;

public class AsyncJobsObserverImpl implements AsyncJobsObserver {

    @NonNull
    @AnyThread
    private final AtomicInteger asyncJobsIdGenerator = new AtomicInteger(0);

    @NonNull
    @AnyThread
    private final Set<AsyncJob> asyncJobs = newSetFromMap(new ConcurrentHashMap<>());

    @NonNull
    @AnyThread
    private final Queue<Listener> listeners = new ConcurrentLinkedQueue<>();

    @AnyThread
    @Override
    public void addListener(@NonNull Listener listener) {
        listeners.add(listener);
    }

    @AnyThread
    @Override
    public void removeListener(@NonNull Listener listener) {
        final boolean removed = listeners.remove(listener);

        if (!removed) {
            throw new IllegalArgumentException("Listener was not registered!");
        }
    }

    @AnyThread
    @Override
    public int numberOfRunningAsyncJobs() {
        return asyncJobs.size();
    }

    @AnyThread
    @NonNull
    @Override
    public AsyncJob asyncJobStarted(@NonNull String name) {
        AsyncJob asyncJob = AsyncJob.create(asyncJobsIdGenerator.getAndIncrement(), name);
        asyncJobs.add(asyncJob);
        notifyListenersAboutChangedNumberOfRunningAsyncJobs();
        return asyncJob;
    }

    @AnyThread
    @Override
    public void asyncJobFinished(@NonNull AsyncJob asyncJob) {
        final boolean removed = asyncJobs.remove(asyncJob);

        if (!removed) {
            throw new IllegalArgumentException("Async job was not registered in the AsyncJobsObserver! Job: " + asyncJob);
        }

        notifyListenersAboutChangedNumberOfRunningAsyncJobs();
    }

    private void notifyListenersAboutChangedNumberOfRunningAsyncJobs() {
        // This is not super consistent since we don't want to do synchronization, but it's okay for us in this case.
        final int numberOfRunningAsyncJobs = numberOfRunningAsyncJobs();

        for (Listener listener : listeners) {
            listener.onNumberOfRunningAsyncJobsChanged(numberOfRunningAsyncJobs);
        }
    }
}
