package com.artemzin.qualitymatters.api;

import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.BuildConfig;
import com.artemzin.qualitymatters.network.ForRestApi;
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
    private final String baseUrl;

    public ApiModule(@NonNull String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides @NonNull @Singleton
    public QualityMattersRestApi provideQualityMattersApi(@NonNull Retrofit retrofit) {
        return retrofit.create(QualityMattersRestApi.class);
    }

    @Provides @NonNull
    public Retrofit provideRetrofit(@NonNull @ForRestApi OkHttpClient okHttpClient, @NonNull ObjectMapper objectMapper) {
        return new Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .validateEagerly(BuildConfig.DEBUG)  // Fail early: check Retrofit configuration at creation time in Debug build.
            .build();
    }
}
