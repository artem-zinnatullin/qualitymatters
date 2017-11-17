package com.artemzin.qualitymatters.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * An interceptor that allows runtime changes to the URL hostname.
 * As per https://gist.github.com/swankjesse/8571a8207a5815cca1fb
 */
public final class HostSelectionInterceptor implements Interceptor {
    /**
     * Using static variable in order to avoid adding this interceptor to ApplicationComponent
     */
    private volatile HttpUrl hostUrl;

    public void setHost(@Nullable HttpUrl host) {
        this.hostUrl = host;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl host = this.hostUrl;
        if (host != null) {
            HttpUrl newUrl = request.url().newBuilder()
                    .host(host.host())
                    .port(host.port())
                    .scheme(host.scheme())
                    .build();
            request = request.newBuilder()
                    .url(newUrl)
                    .build();
        }
        return chain.proceed(request);
    }
}