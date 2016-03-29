package com.artemzin.qualitymatters.network;

import android.support.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import java.util.List;
import javax.inject.Singleton;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

@Module
public class NetworkModule {

    @Provides @NonNull @Singleton
    public OkHttpClient provideOkHttpClient(@OkHttpInterceptors @NonNull List<Interceptor> interceptors,
                                            @OkHttpNetworkInterceptors @NonNull List<Interceptor> networkInterceptors) {
        return newOkHttpBuilder(interceptors, networkInterceptors).build();
    }

    @Provides @NonNull @Singleton @ForRestApi
    public OkHttpClient provideOkHttpClientForRestApi(@OkHttpInterceptors @NonNull List<Interceptor> interceptors,
                                                      @OkHttpNetworkInterceptors @NonNull List<Interceptor> networkInterceptors,
                                                      @NonNull HostSelectionInterceptor hostSelectionInterceptor) {
        return newOkHttpBuilder(interceptors, networkInterceptors)
            .addInterceptor(hostSelectionInterceptor)
            .build();
    }

    private static OkHttpClient.Builder newOkHttpBuilder(@NonNull List<Interceptor> interceptors, @NonNull List<Interceptor> networkInterceptors) {
        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        //noinspection Convert2streamapi
        for (Interceptor interceptor : interceptors) {
            okHttpBuilder.addInterceptor(interceptor);
        }
        //noinspection Convert2streamapi
        for (Interceptor networkInterceptor : networkInterceptors) {
            okHttpBuilder.addNetworkInterceptor(networkInterceptor);
        }
        return okHttpBuilder;
    }
}
