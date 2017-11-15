package com.artemzin.qualitymatters.ui.fragments;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Switch;
import android.widget.TextView;
import com.artemzin.qualitymatters.ApplicationModule;
import com.artemzin.qualitymatters.QualityMattersApp;

import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("PMD.AbstractClassWithoutAnyMethod")
public abstract class BaseFragment extends Fragment {

    @Inject
    @Named(ApplicationModule.MAIN_THREAD_HANDLER)
    Handler mainThreadHandler;

    protected void runOnUiThreadIfFragmentAlive(@NonNull Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper() && isFragmentAlive()) {
            runnable.run();
        } else {
            mainThreadHandler.post(() -> {
                if (isFragmentAlive()) {
                    runnable.run();
                }
            });
        }
    }

    private boolean isFragmentAlive() {
        return getActivity() != null && isAdded() && !isDetached() && getView() != null && !isRemoving();
    }

    @Override
    public void onDestroy() {
        QualityMattersApp.get(getContext()).applicationComponent().leakCanaryProxy().watch(this);
        super.onDestroy();
    }

    protected <V extends TextView> void setTextTo(@Nullable V targetTextView, @NonNull String textToSet) {
        assert targetTextView != null;
        targetTextView.setText(textToSet);
    }

    protected <V extends Switch> void setEnabledTo(@Nullable V targetSwitch, boolean state) {
        assert targetSwitch != null;
        targetSwitch.setChecked(state);
    }
}
