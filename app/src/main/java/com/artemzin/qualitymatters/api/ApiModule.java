package com.artemzin.qualitymatters.api;

import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.BuildConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

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
    public QualityMattersRestApi provideQualityMattersApi(@NonNull OkHttpClient okHttpClient, @NonNull ObjectMapper objectMapper, @NonNull ChangeableBaseUrl changeableBaseUrl) {
        return new Retrofit.Builder()
                .baseUrl(changeableBaseUrl)
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .validateEagerly(BuildConfig.DEBUG)  // Fail early: check Retrofit configuration at creation time in Debug build.
                .build()
                .create(QualityMattersRestApi.class);
    }
}
