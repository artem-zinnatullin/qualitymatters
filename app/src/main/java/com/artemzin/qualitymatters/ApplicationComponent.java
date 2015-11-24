package com.artemzin.qualitymatters;

import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.api.QualityMattersRestApi;
import com.artemzin.qualitymatters.api.ApiModule;
import com.artemzin.qualitymatters.api.ChangeableBaseUrl;
import com.artemzin.qualitymatters.network.NetworkModule;
import com.artemzin.qualitymatters.ui.fragments.ItemsFragment;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class,
        ApiModule.class
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

    @NonNull
    ItemsFragment.ItemsFragmentComponent plus(@NonNull ItemsFragment.ItemsFragmentModule itemsFragmentModule);
}
