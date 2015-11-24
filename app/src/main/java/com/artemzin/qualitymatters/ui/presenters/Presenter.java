package com.artemzin.qualitymatters.ui.presenters;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Base presenter implementation.
 *
 * @param <V> view.
 */
public abstract class Presenter<V> {

    @NonNull
    private final CompositeSubscription subscriptionsToUnsubscribeOnUnbindView = new CompositeSubscription();

    public abstract void bindView(@NonNull V view);

    protected final void unsubscribeOnUnbindView(@NonNull Subscription subscription) {
        subscriptionsToUnsubscribeOnUnbindView.add(subscription);
    }

    @CallSuper
    public void unbindView(@NonNull V view) {
        // Unsubscribe all subscriptions that need to be unsubscribed in this lifecycle state.
        subscriptionsToUnsubscribeOnUnbindView.clear();
    }
}
