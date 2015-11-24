package com.artemzin.qualitymatters.api;

import android.support.annotation.NonNull;

import com.squareup.okhttp.HttpUrl;

import java.util.concurrent.atomic.AtomicReference;

import retrofit.BaseUrl;

/**
 * Such implementation allows us easily change base url in the integration and functional tests!
 */
public class ChangeableBaseUrl implements BaseUrl {

    @NonNull
    private final AtomicReference<HttpUrl> baseUrl;

    ChangeableBaseUrl(@NonNull String baseUrl) {
        this.baseUrl = new AtomicReference<>(HttpUrl.parse(baseUrl));
    }

    public void setBaseUrl(@NonNull String baseUrl) {
        this.baseUrl.set(HttpUrl.parse(baseUrl));
    }

    @Override @NonNull
    public HttpUrl url() {
        return baseUrl.get();
    }
}
