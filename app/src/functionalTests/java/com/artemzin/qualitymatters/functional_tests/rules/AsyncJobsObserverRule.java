package com.artemzin.qualitymatters.functional_tests.rules;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;

import com.artemzin.qualitymatters.functional_tests.TestUtils;
import com.artemzin.qualitymatters.performance.AsyncJobsObserver;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class AsyncJobsObserverRule implements TestRule {

    @Override
    @NonNull
    public Statement apply(@NonNull Statement base, @NonNull Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                final AsyncJobsIdlingResource asyncJobsIdlingResource = new AsyncJobsIdlingResource(TestUtils.app().applicationComponent().asyncJobsObserver());

                try {
                    Espresso.registerIdlingResources(asyncJobsIdlingResource);
                    base.evaluate();
                } finally {
                    asyncJobsIdlingResource.release();
                    Espresso.unregisterIdlingResources(asyncJobsIdlingResource);
                }
            }
        };
    }

    static class AsyncJobsIdlingResource implements IdlingResource {

        @NonNull
        final AsyncJobsObserver asyncJobsObserver;

        @Nullable
        private AsyncJobsObserver.Listener listener;

        AsyncJobsIdlingResource(@NonNull AsyncJobsObserver asyncJobsObserver) {
            this.asyncJobsObserver = asyncJobsObserver;
        }

        @Override
        @NonNull
        public String getName() {
            return "AsyncJobsObserver-IdlingResource";
        }

        @Override
        public boolean isIdleNow() {
            return asyncJobsObserver.numberOfRunningAsyncJobs() == 0;
        }

        @Override
        public void registerIdleTransitionCallback(@NonNull ResourceCallback callback) {
            if (listener != null) { // Just in case if Espresso call this several times per instance.
                asyncJobsObserver.removeListener(listener);
            }

            listener = numberOfRunningAsyncJobs -> {
                if (numberOfRunningAsyncJobs == 0) {
                    callback.onTransitionToIdle();
                }
            };

            asyncJobsObserver.addListener(listener);
        }

        public void release() {
            if (listener != null) {
                asyncJobsObserver.removeListener(listener);
            }
        }
    }
}
