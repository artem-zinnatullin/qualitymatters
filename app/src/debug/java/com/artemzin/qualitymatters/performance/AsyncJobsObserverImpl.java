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
    private final AtomicInteger asyncJobsIdGenerator = new AtomicInteger(0);

    @NonNull
    private final Set<AsyncJob> asyncJobs = newSetFromMap(new ConcurrentHashMap<>());

    @NonNull
    private final Queue<Listener> listeners = new ConcurrentLinkedQueue<>();

    @AnyThread
    @Override
    public void addListener(@NonNull Listener listener) {
        listeners.add(listener);
    }

    @AnyThread
    @Override
    public void removeListener(@NonNull Listener listener) {
        listeners.remove(listener);
    }

    @AnyThread
    @Override
    public int numberOfRunningAsyncJobs() {
        return asyncJobs.size();
    }

    @AnyThread
    @NonNull
    @Override
    public AsyncJob asyncJobStarted() {
        AsyncJob asyncJob = AsyncJob.create(asyncJobsIdGenerator.getAndIncrement());
        asyncJobs.add(asyncJob);
        return asyncJob;
    }

    @AnyThread
    @Override
    public void asyncJobFinished(@NonNull AsyncJob asyncJob) {
        final boolean removed = asyncJobs.remove(asyncJob);

        if (!removed) {
            throw new AssertionError("Async job were registered in the AsyncJobsObserver! Job: " + asyncJob);
        }

        if (numberOfRunningAsyncJobs() == 0) {
            for (Listener listener : listeners) {
                listener.onNoMoreAsyncJobsRunning();
            }
        }
    }
}
