package com.artemzin.qualitymatters.ui.fragments;

import com.artemzin.qualitymatters.QualityMattersRobolectricUnitTestRunner;
import com.artemzin.qualitymatters.ui.presenters.DeveloperSettingsPresenter;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;

@RunWith(QualityMattersRobolectricUnitTestRunner.class)
public class DeveloperSettingsFragmentTest {

    @Test
    public void syncDeveloperSettings() {
        DeveloperSettingsFragment fragment = new DeveloperSettingsFragment();

        fragment.presenter = mock(DeveloperSettingsPresenter.class);

        fragment.presenter.syncDeveloperSettings();

        //TODO: get TextViews and Switches from fragment layout and check if they change after syncDeveloperSettings()
    }
}
