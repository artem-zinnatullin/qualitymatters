package com.artemzin.qualitymatters;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

// It's a Dagger module that provides application level dependencies.
@Module
public class ApplicationModule {

    public static final String MAIN_THREAD_HANDLER = "main_thread_handler";

    @NonNull
    private final QualityMattersApp qualityMattersApp;

    public ApplicationModule(@NonNull QualityMattersApp qualityMattersApp) {
        this.qualityMattersApp = qualityMattersApp;
    }

    @Provides @NonNull @Singleton
    public QualityMattersApp provideQualityMattersApp() {
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
    public Picasso providePicasso(@NonNull QualityMattersApp qualityMattersApp, @NonNull OkHttpClient okHttpClient) {
        return new Picasso.Builder(qualityMattersApp)
                .downloader(new OkHttpDownloader(okHttpClient))
                .build();
    }
}
