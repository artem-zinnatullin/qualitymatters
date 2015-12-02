package com.artemzin.qualitymatters.ui.presenters;

import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.developer_settings.DeveloperSettingsModelImpl;
import com.artemzin.qualitymatters.ui.views.DeveloperSettingsView;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeveloperSettingsPresenterTest {

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private DeveloperSettingsModelImpl developerSettingsModel;

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private DeveloperSettingsPresenter developerSettingsPresenter;

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private DeveloperSettingsView developerSettingsView;

    @Before
    public void beforeEachTest() {
        developerSettingsModel = mock(DeveloperSettingsModelImpl.class);
        developerSettingsPresenter = new DeveloperSettingsPresenter(developerSettingsModel);
        developerSettingsView = mock(DeveloperSettingsView.class);
    }

    @Test
    public void bindView_shouldSendStethoEnabledStateToTheView() {
        when(developerSettingsModel.isStethoEnabled()).thenReturn(true);

        developerSettingsPresenter.bindView(developerSettingsView);
        verify(developerSettingsView).changeStethoState(true);
        verify(developerSettingsModel).isStethoEnabled();
    }

    @Test
    public void bindView_shouldSendStethoDisabledStateToTheView() {
        when(developerSettingsModel.isStethoEnabled()).thenReturn(false);

        developerSettingsPresenter.bindView(developerSettingsView);
        verify(developerSettingsView).changeStethoState(false);
        verify(developerSettingsModel).isStethoEnabled();
    }

    @Test
    public void bindView_shouldSendLeakCanaryEnabledStateToTheView() {
        when(developerSettingsModel.isLeakCanaryEnabled()).thenReturn(true);

        developerSettingsPresenter.bindView(developerSettingsView);
        verify(developerSettingsView).changeLeakCanaryState(true);
        verify(developerSettingsModel).isLeakCanaryEnabled();
    }

    @Test
    public void bindView_shouldSendLeakCanaryDisabledStateToTheView() {
        when(developerSettingsModel.isLeakCanaryEnabled()).thenReturn(false);

        developerSettingsPresenter.bindView(developerSettingsView);
        verify(developerSettingsView).changeLeakCanaryState(false);
        verify(developerSettingsModel).isLeakCanaryEnabled();
    }

    @Test
    public void bindView_shouldSendTinyDancerEnabledStateToTheView() {
        when(developerSettingsModel.isTinyDancerEnabled()).thenReturn(true);

        developerSettingsPresenter.bindView(developerSettingsView);
        verify(developerSettingsView).changeTinyDancerState(true);
        verify(developerSettingsModel).isTinyDancerEnabled();
    }

    @Test
    public void bindView_shouldSendTinyDancerDisabledStateToTheView() {
        when(developerSettingsModel.isTinyDancerEnabled()).thenReturn(false);

        developerSettingsPresenter.bindView(developerSettingsView);
        verify(developerSettingsView).changeTinyDancerState(false);
        verify(developerSettingsModel).isTinyDancerEnabled();
    }

    @Test
    public void changeStethoState_shouldNoOpIfStateAlreadySameAndEnabled() {
        when(developerSettingsModel.isStethoEnabled()).thenReturn(true);

        developerSettingsPresenter.bindView(developerSettingsView);
        developerSettingsPresenter.changeStethoState(true);

        verify(developerSettingsModel, never()).changeStethoState(anyBoolean());
        verify(developerSettingsView, never()).showMessage(anyString());
        verify(developerSettingsView, never()).showAppNeedsToBeRestarted();
    }

    @Test
    public void changeStethoState_shouldNoOpIfStateAlreadySameAndDisabled() {
        when(developerSettingsModel.isStethoEnabled()).thenReturn(false);

        developerSettingsPresenter.bindView(developerSettingsView);
        developerSettingsPresenter.changeStethoState(false);

        verify(developerSettingsModel, never()).changeStethoState(anyBoolean());
        verify(developerSettingsView, never()).showMessage(anyString());
        verify(developerSettingsView, never()).showAppNeedsToBeRestarted();
    }

    @Test
    public void changeStethoState_shouldEnableStethoAndNotifyView() {
        developerSettingsPresenter.bindView(developerSettingsView);

        developerSettingsPresenter.changeStethoState(true);
        verify(developerSettingsModel).changeStethoState(true);
        verify(developerSettingsView).showMessage("Stetho was enabled");
        verify(developerSettingsView, never()).showAppNeedsToBeRestarted();
    }

    @Test
    public void changeStethoState_shouldDisableStethoAndNotifyView() {
        developerSettingsPresenter.bindView(developerSettingsView);

        when(developerSettingsModel.isStethoEnabled()).thenReturn(true);
        developerSettingsPresenter.changeStethoState(false);
        verify(developerSettingsModel).changeStethoState(false);
        verify(developerSettingsView).showMessage("Stetho was disabled");
        verify(developerSettingsView).showAppNeedsToBeRestarted();
    }

    @Test
    public void changeStethoState_shouldDisableStethoAndNotifyViewAndAskAppRestartIfStethoWasAlreadyEnabled() {
        developerSettingsPresenter.bindView(developerSettingsView);

        when(developerSettingsModel.isStethoEnabled()).thenReturn(true);

        developerSettingsPresenter.changeStethoState(false);
        verify(developerSettingsModel).changeStethoState(false);
        verify(developerSettingsView).showMessage("Stetho was disabled");

        verify(developerSettingsView).showAppNeedsToBeRestarted();
    }

    @Test
    public void changeLeakCanaryState_shouldNoOpIfStateAlreadySameAndEnabled() {
        when(developerSettingsModel.isLeakCanaryEnabled()).thenReturn(true);

        developerSettingsPresenter.bindView(developerSettingsView);
        developerSettingsPresenter.changeLeakCanaryState(true);

        verify(developerSettingsModel, never()).changeLeakCanaryState(anyBoolean());
        verify(developerSettingsView, never()).showMessage(anyString());
        verify(developerSettingsView, never()).showAppNeedsToBeRestarted();
    }

    @Test
    public void changeLeakCanaryState_shouldNoOpIfStateAlreadySameAndDisabled() {
        when(developerSettingsModel.isLeakCanaryEnabled()).thenReturn(false);

        developerSettingsPresenter.bindView(developerSettingsView);
        developerSettingsPresenter.changeLeakCanaryState(false);

        verify(developerSettingsModel, never()).changeLeakCanaryState(anyBoolean());
        verify(developerSettingsView, never()).showMessage(anyString());
        verify(developerSettingsView, never()).showAppNeedsToBeRestarted();
    }

    @Test
    public void changeLeakCanaryState_shouldEnableLeakCanaryAndNotifyView() {
        developerSettingsPresenter.bindView(developerSettingsView);

        developerSettingsPresenter.changeLeakCanaryState(true);
        verify(developerSettingsModel).changeLeakCanaryState(true);
        verify(developerSettingsView).showMessage("LeakCanary was enabled");
        verify(developerSettingsView).showAppNeedsToBeRestarted();
    }

    @Test
    public void changeLeakCanaryState_shouldDisableLeakCanaryAndNotifyView() {
        developerSettingsPresenter.bindView(developerSettingsView);

        when(developerSettingsModel.isLeakCanaryEnabled()).thenReturn(true);

        developerSettingsPresenter.changeLeakCanaryState(false);
        verify(developerSettingsModel).changeLeakCanaryState(false);
        verify(developerSettingsView).showMessage("LeakCanary was disabled");
        verify(developerSettingsView).showAppNeedsToBeRestarted();
    }

    @Test
    public void changeTinyDancerState_shouldNoOpIfStateAlreadySameAndEnabled() {
        when(developerSettingsModel.isTinyDancerEnabled()).thenReturn(true);

        developerSettingsPresenter.bindView(developerSettingsView);
        developerSettingsPresenter.changeTinyDancerState(true);

        verify(developerSettingsModel, never()).changeTinyDancerState(anyBoolean());
        verify(developerSettingsView, never()).showMessage(anyString());
        verify(developerSettingsView, never()).showAppNeedsToBeRestarted();
    }

    @Test
    public void changeTinyDancerState_shouldNoOpIfStateAlreadySameAndDisabled() {
        when(developerSettingsModel.isTinyDancerEnabled()).thenReturn(false);

        developerSettingsPresenter.bindView(developerSettingsView);
        developerSettingsPresenter.changeTinyDancerState(false);

        verify(developerSettingsModel, never()).changeTinyDancerState(anyBoolean());
        verify(developerSettingsView, never()).showMessage(anyString());
        verify(developerSettingsView, never()).showAppNeedsToBeRestarted();
    }

    @Test
    public void changeTinyDancerState_shouldEnableTinyDancerAndNotifyView() {
        developerSettingsPresenter.bindView(developerSettingsView);

        developerSettingsPresenter.changeTinyDancerState(true);
        verify(developerSettingsModel).changeTinyDancerState(true);
        verify(developerSettingsView).showMessage("TinyDancer was enabled");
        verify(developerSettingsView, never()).showAppNeedsToBeRestarted();
    }

    @Test
    public void changeTinyDancerState_shouldDisableTinyDancerAndNotifyView() {
        when(developerSettingsModel.isTinyDancerEnabled()).thenReturn(true);
        developerSettingsPresenter.bindView(developerSettingsView);

        developerSettingsPresenter.changeTinyDancerState(false);
        verify(developerSettingsModel).changeTinyDancerState(false);
        verify(developerSettingsView).showMessage("TinyDancer was disabled");
        verify(developerSettingsView, never()).showAppNeedsToBeRestarted();
    }
}