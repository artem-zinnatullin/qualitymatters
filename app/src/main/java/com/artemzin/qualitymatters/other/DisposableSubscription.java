package com.artemzin.qualitymatters.other;

import android.support.annotation.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

import java.util.concurrent.atomic.AtomicBoolean;

public class DisposableSubscription implements Disposable {

    @NonNull
    private final AtomicBoolean disposed = new AtomicBoolean(false);

    @NonNull
    private final Action disposeAction;

    public DisposableSubscription(@NonNull Action disposeAction) {
        this.disposeAction = disposeAction;
    }

    @Override
    public void dispose() {
        if (disposed.compareAndSet(false, true)) {
            try {
                disposeAction.run();
            } catch (Exception e) {
                throw new RuntimeException(e); // RxJava2 Action throws an exception. Pass it on
            }
        }
    }

    @Override
    public boolean isDisposed() {
        return disposed.get();
    }
}
