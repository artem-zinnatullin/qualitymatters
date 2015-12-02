package com.artemzin.qualitymatters.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.artemzin.qualitymatters.QualityMattersApp;
import com.artemzin.qualitymatters.R;
import com.artemzin.qualitymatters.performance.AnyThread;
import com.artemzin.qualitymatters.ui.presenters.DeveloperSettingsPresenter;
import com.artemzin.qualitymatters.ui.views.DeveloperSettingsView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class DeveloperSettingsFragment extends BaseFragment implements DeveloperSettingsView {

    @Inject
    DeveloperSettingsPresenter presenter;

    @Bind(R.id.developer_settings_stetho_switch)
    Switch stethoSwitch;

    @Bind(R.id.developer_settings_leak_canary_switch)
    Switch leakCanarySwitch;

    @Bind(R.id.developer_settings_tiny_dancer_switch)
    Switch tinyDancerSwitch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QualityMattersApp.get(getContext()).applicationComponent().plusDeveloperSettingsComponent().inject(this);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_developer_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        presenter.bindView(this);
    }

    @OnCheckedChanged(R.id.developer_settings_stetho_switch)
    void onStethoSwitchCheckedChanged(boolean checked) {
        presenter.changeStethoState(checked);
    }

    @OnCheckedChanged(R.id.developer_settings_tiny_dancer_switch)
    void onTinyDancerSwitchCheckedChanged(boolean checked) {
        presenter.changeTinyDancerState(checked);
    }

    @Override
    @AnyThread
    public void changeStethoState(boolean enabled) {
        runOnUiThreadIfFragmentAlive(() -> {
            assert stethoSwitch != null;
            stethoSwitch.setChecked(enabled);
        });
    }

    @Override
    @AnyThread
    public void changeLeakCanaryState(boolean enabled) {
        runOnUiThreadIfFragmentAlive(() -> {
            assert leakCanarySwitch != null;
            leakCanarySwitch.setChecked(enabled);
        });
    }

    @Override
    @AnyThread
    public void changeTinyDancerState(boolean enabled) {
        runOnUiThreadIfFragmentAlive(() -> {
            assert tinyDancerSwitch != null;
            tinyDancerSwitch.setChecked(enabled);
        });
    }

    @SuppressLint("ShowToast") // Yeah, Lambdas and Lint are not good friends…
    @Override
    @AnyThread
    public void showMessage(@NonNull String message) {
        runOnUiThreadIfFragmentAlive(() -> Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show());
    }

    @OnCheckedChanged(R.id.developer_settings_leak_canary_switch)
    void onLeakCanarySwitchCheckedChanged(boolean checked) {
        presenter.changeLeakCanaryState(checked);
    }

    @SuppressLint("ShowToast") // Yeah, Lambdas and Lint are not good friends…
    @Override
    @AnyThread
    public void showAppNeedsToBeRestarted() {
        runOnUiThreadIfFragmentAlive(() -> Toast.makeText(getContext(), "To apply new settings app needs to be restarted", Toast.LENGTH_LONG).show());
    }

    @Override
    public void onDestroyView() {
        presenter.unbindView(this);
        super.onDestroyView();
    }
}
