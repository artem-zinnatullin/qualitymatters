package com.artemzin.androiddevelopmentculture;

import android.support.annotation.NonNull;

import com.artemzin.androiddevelopmentculture.api.ADCApi;
import com.artemzin.androiddevelopmentculture.api.ApiModule;
import com.artemzin.androiddevelopmentculture.api.ChangeableBaseUrl;
import com.artemzin.androiddevelopmentculture.network.NetworkModule;
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

    // Provide ADCApi from the real app to the tests without need in injection to the test.
    @NonNull
    ADCApi adcApi();

    @NonNull
    ChangeableBaseUrl changeableBaseUrl();
}
