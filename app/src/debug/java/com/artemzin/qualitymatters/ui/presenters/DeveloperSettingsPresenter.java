package com.artemzin.qualitymatters.ui.presenters;

import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.developer_settings.DeveloperSettingsModelImpl;
import com.artemzin.qualitymatters.ui.views.DeveloperSettingsView;

public class DeveloperSettingsPresenter extends Presenter<DeveloperSettingsView> {

    @NonNull
    private final DeveloperSettingsModelImpl developerSettingsModel;

    public DeveloperSettingsPresenter(@NonNull DeveloperSettingsModelImpl developerSettingsModel) {
        this.developerSettingsModel = developerSettingsModel;
    }

    @Override
    public void bindView(@NonNull DeveloperSettingsView view) {
        super.bindView(view);
        view.changeStethoState(developerSettingsModel.isStethoEnabled());
        view.changeLeakCanaryState(developerSettingsModel.isLeakCanaryEnabled());
    }

    public void changeStethoState(boolean enabled) {
        if (developerSettingsModel.isStethoEnabled() == enabled) {
            return; // no-op
        }

        boolean stethoWasEnabled = developerSettingsModel.isStethoEnabled();

        developerSettingsModel.changeStethoState(enabled);
        developerSettingsModel.apply();

        final DeveloperSettingsView view = view();

        if (view != null) {
            view.showMessage("Stetho was " + booleanToEnabledDisabled(enabled));

            if (stethoWasEnabled) {
                view.showAppNeedsToBeRestarted();
            }
        }
    }

    public void changeLeakCanaryState(boolean enabled) {
        if (developerSettingsModel.isLeakCanaryEnabled() == enabled) {
            return; // no-op
        }

        developerSettingsModel.changeLeakCanaryState(enabled);
        developerSettingsModel.apply();

        final DeveloperSettingsView view = view();

        if (view != null) {
            view.showMessage("LeakCanary was " + booleanToEnabledDisabled(enabled));
            view.showAppNeedsToBeRestarted(); // LeakCanary can not be enabled on demand (or it's possible?)
        }
    }

    @NonNull
    private static String booleanToEnabledDisabled(boolean enabled) {
        return enabled ? "enabled" : "disabled";
    }
}
