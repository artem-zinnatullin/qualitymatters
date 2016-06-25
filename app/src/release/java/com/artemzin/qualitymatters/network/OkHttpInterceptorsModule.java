package com.artemzin.qualitymatters.network;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * Provides OkHttp interceptors for release build.
 */
@Module
public class OkHttpInterceptorsModule {

    @Provides @Singleton @NonNull
    public HostSelectionInterceptor provideHostSelectionInterceptor() {
        return new HostSelectionInterceptor();
    }

    @Provides @OkHttpInterceptors @Singleton @NonNull
    public List<Interceptor> provideOkHttpInterceptors(@NonNull HostSelectionInterceptor hostSelectionInterceptor) {
        return singletonList(hostSelectionInterceptor);
    }

    @Provides @OkHttpNetworkInterceptors @Singleton @NonNull
    public List<Interceptor> provideOkHttpNetworkInterceptors() {
        return emptyList();
    }
}
