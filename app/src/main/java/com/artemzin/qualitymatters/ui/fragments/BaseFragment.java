package com.artemzin.qualitymatters.ui.fragments;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

@SuppressWarnings("PMD.AbstractClassWithoutAnyMethod")
public abstract class BaseFragment extends Fragment {

    // Due to bug (https://github.com/google/dagger/issues/214) in Dagger 2 we can not inject handler here, sorry.
    @NonNull
    private static final Handler MAIN_THREAD_HANDLER = new Handler(Looper.getMainLooper());

    protected void runOnUiThreadIfFragmentAlive(@NonNull Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper() && isFragmentAlive()) {
            runnable.run();
        } else {
            MAIN_THREAD_HANDLER.post(() -> {
                if (isFragmentAlive()) {
                    runnable.run();
                }
            });
        }
    }

    private boolean isFragmentAlive() {
        return getActivity() != null && isAdded() && !isDetached() && getView() != null && !isRemoving();
    }
}
