package com.artemzin.qualitymatters.network;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;

import static java.util.Collections.emptyList;

/**
 * Provides OkHttp interceptors for release build.
 */
@Module
public class OkHttpInterceptorsModule {

    @Provides @OkHttpInterceptors @Singleton @NonNull
    public List<Interceptor> provideOkHttpInterceptors() {
        return emptyList();
    }

    @Provides @OkHttpNetworkInterceptors @Singleton @NonNull
    public List<Interceptor> provideOkHttpNetworkInterceptors() {
        return emptyList();
    }
}
