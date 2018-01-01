package com.artemzin.qualitymatters.api;

import android.support.annotation.NonNull;
import com.artemzin.qualitymatters.BuildConfig;
import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;

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
    public QualityMattersRestApi provideQualityMattersApi(@NonNull OkHttpClient okHttpClient, @NonNull Gson gson, @NonNull ChangeableBaseUrl changeableBaseUrl) {
        return new Retrofit.Builder()
                .baseUrl(changeableBaseUrl.url())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .validateEagerly(BuildConfig.DEBUG)  // Fail early: check Retrofit configuration at creation time in Debug build.
                .build()
                .create(QualityMattersRestApi.class);
    }
}
