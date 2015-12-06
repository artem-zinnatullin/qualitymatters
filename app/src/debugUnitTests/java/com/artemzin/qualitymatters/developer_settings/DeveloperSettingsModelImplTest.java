package com.artemzin.qualitymatters.developer_settings;

import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.QualityMattersApp;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeveloperSettingsModelImplTest {

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private DeveloperSettingsModelImpl developerSettingsModel;

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private DeveloperSettings developerSettings;

    @Before
    public void beforeEachTest() {
        developerSettings = mock(DeveloperSettings.class);
        developerSettingsModel = new DeveloperSettingsModelImpl(
                mock(QualityMattersApp.class),
                developerSettings,
                mock(OkHttpClient.class),
                new HttpLoggingInterceptor(),
                mock(LeakCanaryProxy.class)
        );
    }

    @Test
    public void isStethoEnabled_shouldReturnValueFromDeveloperSettings() {
        when(developerSettings.isStethoEnabled()).thenReturn(true);
        assertThat(developerSettingsModel.isStethoEnabled()).isTrue();
        verify(developerSettings).isStethoEnabled();

        when(developerSettings.isStethoEnabled()).thenReturn(false);
        assertThat(developerSettingsModel.isStethoEnabled()).isFalse();
        verify(developerSettings, times(2)).isStethoEnabled();
    }

    @Test
    public void isLeakCanaryEnabled_shouldReturnValueFromDeveloperSettings() {
        when(developerSettings.isLeakCanaryEnabled()).thenReturn(true);
        assertThat(developerSettingsModel.isLeakCanaryEnabled()).isTrue();
        verify(developerSettings).isLeakCanaryEnabled();

        when(developerSettings.isLeakCanaryEnabled()).thenReturn(false);
        assertThat(developerSettingsModel.isLeakCanaryEnabled()).isFalse();
        verify(developerSettings, times(2)).isLeakCanaryEnabled();
    }

    @Test
    public void isTinyDancerEnabled_shouldReturnValueFromDeveloperSettings() {
        when(developerSettings.isTinyDancerEnabled()).thenReturn(true);
        assertThat(developerSettingsModel.isTinyDancerEnabled()).isTrue();
        verify(developerSettings).isTinyDancerEnabled();

        when(developerSettings.isTinyDancerEnabled()).thenReturn(false);
        assertThat(developerSettingsModel.isTinyDancerEnabled()).isFalse();
        verify(developerSettings, times(2)).isTinyDancerEnabled();
    }

    @Test
    public void getHttpLoggingInterceptor_shouldReturnValueFromDeveloperSettings() {
        for (HttpLoggingInterceptor.Level loggingLevel : HttpLoggingInterceptor.Level.values()) {
            when(developerSettings.getHttpLoggingLevel()).thenReturn(loggingLevel);
            assertThat(developerSettingsModel.getHttpLoggingLevel()).isEqualTo(loggingLevel);
        }
    }

    // To test apply() method we will need a lof of abstractions over the libraries used
    // for Developer Settings, because most of them initialized statically and hardly mockable/verifiable :(
    // So, sorry, no tests for apply(). But, feel free to PR!
}