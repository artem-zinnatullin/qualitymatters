package com.artemzin.androiddevelopmentculture.api;

import android.support.annotation.NonNull;

import com.artemzin.androiddevelopmentculture.BuildConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

@Module
public class ApiModule {

    @NonNull
    private final ChangeableBaseUrl changeableBaseUrl;

    public ApiModule(@NonNull String baseUrl) {
        changeableBaseUrl = new ChangeableBaseUrl(baseUrl);
    }

    @Provides @NonNull @Singleton
    public ChangeableBaseUrl provideChangeableBaseUrl() {
        return changeableBaseUrl;
    }

    @Provides @NonNull @Singleton
    public ADCApi provideAdcApi(@NonNull OkHttpClient okHttpClient, @NonNull ObjectMapper objectMapper, @NonNull ChangeableBaseUrl changeableBaseUrl) {
        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(changeableBaseUrl)
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        // Fail early: check Retrofit configuration at creation time
        if (BuildConfig.DEBUG) {
            builder.validateEagerly();
        }

        return builder.build().create(ADCApi.class);
    }
}
