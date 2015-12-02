package com.artemzin.qualitymatters;

import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.api.ApiModule;
import com.artemzin.qualitymatters.api.ChangeableBaseUrl;
import com.artemzin.qualitymatters.api.QualityMattersRestApi;
import com.artemzin.qualitymatters.developer_settings.DeveloperSettingsComponent;
import com.artemzin.qualitymatters.developer_settings.DeveloperSettingsModule;
import com.artemzin.qualitymatters.developer_settings.LeakCanaryProxy;
import com.artemzin.qualitymatters.network.NetworkModule;
import com.artemzin.qualitymatters.performance.AsyncJobsModule;
import com.artemzin.qualitymatters.performance.AsyncJobsObserver;
import com.artemzin.qualitymatters.ui.activities.MainActivity;
import com.artemzin.qualitymatters.ui.fragments.ItemsFragment;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class,
        ApiModule.class,
        AsyncJobsModule.class,
        DeveloperSettingsModule.class
})
public interface ApplicationComponent {

    // Provide ObjectMapper from the real app to the tests without need in injection to the test.
    @NonNull
    ObjectMapper objectMapper();

    // Provide QualityMattersRestApi from the real app to the tests without need in injection to the test.
    @NonNull
    QualityMattersRestApi qualityMattersApi();

    @NonNull
    ChangeableBaseUrl changeableBaseUrl();

    // Provide AsyncJobObserver from the real app to the tests without need in injection to the test.
    @NonNull
    AsyncJobsObserver asyncJobsObserver();

    // Provide LeakCanary without injection to leave
    @NonNull
    LeakCanaryProxy leakCanaryProxy();

    @NonNull
    ItemsFragment.ItemsFragmentComponent plus(@NonNull ItemsFragment.ItemsFragmentModule itemsFragmentModule);

    @NonNull
    DeveloperSettingsComponent plusDeveloperSettingsComponent();

    void inject(@NonNull QualityMattersApp qualityMattersApp);

    void inject(@NonNull MainActivity mainActivity);
}
