package com.artemzin.qualitymatters.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Such implementation allows us easily change base url in the integration and functional tests!
 */
public class HostSelectionInterceptor implements Interceptor {

    @Nullable
    private volatile String baseUrl;

    @Override @NonNull
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        final String baseUrl = this.baseUrl;

        if (baseUrl != null) {
            request = modifyRequest(request, baseUrl);
        }
        return chain.proceed(request);
    }

    public void setBaseUrl(@Nullable String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @NonNull
    Request modifyRequest(@NonNull final Request request, @Nullable String baseUrl) throws MalformedURLException {
        final URL url = new URL(baseUrl);
        final HttpUrl newUrl = request.url()
            .newBuilder()
            .scheme(url.getProtocol())
            .host(url.getHost())
            .port(url.getPort())
            .build();
        return request.newBuilder()
            .url(newUrl)
            .build();
    }

    @Nullable
    public String getBaseUrl() {
        return baseUrl;
    }
}
