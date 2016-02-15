package com.artemzin.qualitymatters.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.artemzin.qualitymatters.QualityMattersApp;
import com.artemzin.qualitymatters.R;
import com.artemzin.qualitymatters.performance.AnyThread;
import com.artemzin.qualitymatters.ui.adapters.DeveloperSettingsSpinnerAdapter;
import com.artemzin.qualitymatters.ui.presenters.DeveloperSettingsPresenter;
import com.artemzin.qualitymatters.ui.views.DeveloperSettingsView;
import com.github.pedrovgs.lynx.LynxActivity;
import com.github.pedrovgs.lynx.LynxConfig;
import okhttp3.logging.HttpLoggingInterceptor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class DeveloperSettingsFragment extends BaseFragment implements DeveloperSettingsView {

    @Inject
    DeveloperSettingsPresenter presenter;

    @Inject
    LynxConfig lynxConfig;

    @Bind(R.id.developer_settings_git_sha_text_view)
    TextView gitShaTextView;

    @Bind(R.id.developer_settings_build_date_text_view)
    TextView buildDateTextView;

    @Bind(R.id.developer_settings_build_version_code_text_view)
    TextView buildVersionCodeTextView;

    @Bind(R.id.developer_settings_build_version_name_text_view)
    TextView buildVersionNameTextView;

    @Bind(R.id.developer_settings_stetho_switch)
    Switch stethoSwitch;

    @Bind(R.id.developer_settings_leak_canary_switch)
    Switch leakCanarySwitch;

    @Bind(R.id.developer_settings_tiny_dancer_switch)
    Switch tinyDancerSwitch;

    @Bind(R.id.developer_settings_http_logging_level_spinner)
    Spinner httpLoggingLevelSpinner;

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

        httpLoggingLevelSpinner
                .setAdapter(new DeveloperSettingsSpinnerAdapter<>(getActivity().getLayoutInflater())
                .setSelectionOptions(HttpLoggingLevel.allValues()));

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

    @OnItemSelected(R.id.developer_settings_http_logging_level_spinner)
    void onHttpLoggingLevelChanged(int position) {
        presenter.changeHttpLoggingLevel(((HttpLoggingLevel) httpLoggingLevelSpinner.getItemAtPosition(position)).loggingLevel);
    }

    @Override
    @AnyThread
    public void changeGitSha(@NonNull String gitSha) {
        runOnUiThreadIfFragmentAlive(() -> {
            assert gitShaTextView != null;
            gitShaTextView.setText(gitSha);
        });
    }

    @Override
    @AnyThread
    public void changeBuildDate(@NonNull String date) {
        runOnUiThreadIfFragmentAlive(() -> {
            assert buildDateTextView != null;
            buildDateTextView.setText(date);
        });
    }

    @Override
    @AnyThread
    public void changeBuildVersionCode(@NonNull String versionCode) {
        runOnUiThreadIfFragmentAlive(() -> {
            assert buildVersionCodeTextView != null;
            buildVersionCodeTextView.setText(versionCode);
        });
    }

    @Override
    @AnyThread
    public void changeBuildVersionName(@NonNull String versionName) {
        runOnUiThreadIfFragmentAlive(() -> {
            assert buildVersionNameTextView != null;
            buildVersionNameTextView.setText(versionName);
        });
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

    @Override
    @AnyThread
    public void changeHttpLoggingLevel(@NonNull HttpLoggingInterceptor.Level loggingLevel) {
        runOnUiThreadIfFragmentAlive(() -> {
            assert httpLoggingLevelSpinner != null;

            for (int position = 0, count = httpLoggingLevelSpinner.getCount(); position < count; position++) {
                if (loggingLevel == ((HttpLoggingLevel) httpLoggingLevelSpinner.getItemAtPosition(position)).loggingLevel) {
                    httpLoggingLevelSpinner.setSelection(position);
                    return;
                }
            }

            throw new IllegalStateException("Unknown loggingLevel, looks like a serious bug. Passed loggingLevel = " + loggingLevel);
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

    @OnClick(R.id.b_show_log)
    void showLog() {
        Context context = getActivity();
        context.startActivity(LynxActivity.getIntent(context, lynxConfig));
    }

    @Override
    public void onDestroyView() {
        presenter.unbindView(this);
        super.onDestroyView();
    }

    private static class HttpLoggingLevel implements DeveloperSettingsSpinnerAdapter.SelectionOption {

        @NonNull
        public final HttpLoggingInterceptor.Level loggingLevel;

        public HttpLoggingLevel(@NonNull HttpLoggingInterceptor.Level loggingLevel) {
            this.loggingLevel = loggingLevel;
        }

        @NonNull
        @Override
        public String title() {
            return loggingLevel.toString();
        }

        @NonNull
        static List<HttpLoggingLevel> allValues() {
            final HttpLoggingInterceptor.Level[] loggingLevels = HttpLoggingInterceptor.Level.values();
            final List<HttpLoggingLevel> values = new ArrayList<>(loggingLevels.length);
            for (HttpLoggingInterceptor.Level loggingLevel : loggingLevels) {
                values.add(new HttpLoggingLevel(loggingLevel));
            }
            return values;
        }
    }
}
