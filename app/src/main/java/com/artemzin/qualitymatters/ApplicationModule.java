package com.artemzin.qualitymatters;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class ApplicationModule {

    public static final String MAIN_THREAD_HANDLER = "main_thread_handler";

    @NonNull
    private final Application qualityMattersApp;

    public ApplicationModule(@NonNull Application qualityMattersApp) {
        this.qualityMattersApp = qualityMattersApp;
    }

    @Provides @NonNull @Singleton
    public Application provideQualityMattersApp() {
        return qualityMattersApp;
    }

    @Provides @NonNull @Singleton
    public ObjectMapper provideObjectMapper() {
        return new ObjectMapper();
    }

    @Provides @NonNull @Named(MAIN_THREAD_HANDLER) @Singleton
    public Handler provideMainThreadHandler() {
        return new Handler(Looper.getMainLooper());
    }

    @Provides @NonNull @Singleton
    public Picasso providePicasso(@NonNull Application qualityMattersApp, @NonNull OkHttpClient okHttpClient) {
        return new Picasso.Builder(qualityMattersApp)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
    }
}
