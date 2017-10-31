package com.artemzin.qualitymatters.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;

import java.io.IOException;
import java.net.URI;

/**
 * An interceptor that allows runtime changes to the URL hostname.
 * As per https://gist.github.com/swankjesse/8571a8207a5815cca1fb
 */
public final class HostSelectionInterceptor implements Interceptor {
    /**
     * Using static variable in order to avoid adding this interceptor to ApplicationComponent
     */
    private volatile String host;

    public void setHost(@Nullable String host) {
        this.host = host;
    }

    @Override
    public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        String host = this.host;
        if (host != null) {
            URI uri = URI.create(host);
            HttpUrl newUrl = request.url().newBuilder()
                    .host(uri.getHost())
                    .port(uri.getPort())
                    .scheme("http") //in order to avoid SSL Handshake failure
                    .build();
            request = request.newBuilder()
                    .url(newUrl)
                    .build();
        }
        return chain.proceed(request);
    }
}