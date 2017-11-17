package com.artemzin.qualitymatters.api;

import android.support.annotation.NonNull;
import com.artemzin.qualitymatters.BuildConfig;
import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;

@Module
public class ApiModule {

    @NonNull
    private final String baseUrl;

    public ApiModule(@NonNull String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    @NonNull
    @Singleton
    public QualityMattersRestApi provideQualityMattersApi(@NonNull OkHttpClient okHttpClient, @NonNull Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .validateEagerly(BuildConfig.DEBUG)  // Fail early: check Retrofit configuration at creation time in Debug build.
                .build()
                .create(QualityMattersRestApi.class);
    }
}
