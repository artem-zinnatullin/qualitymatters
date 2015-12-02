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
    }

    public void changeStethoState(boolean enabled) {
        boolean stethoWasEnabled = developerSettingsModel.isStethoEnabled();

        developerSettingsModel.changeStethoState(enabled);
        developerSettingsModel.apply();

        if (stethoWasEnabled) {
            //noinspection ConstantConditions
            view().showAppNeedsToBeRestarted();
        }
    }
}
