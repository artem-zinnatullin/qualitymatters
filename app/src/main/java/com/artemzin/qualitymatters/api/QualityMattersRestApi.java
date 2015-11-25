package com.artemzin.qualitymatters.api;

import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.api.entities.Item;

import java.util.List;

import retrofit.http.GET;
import rx.Single;

public interface QualityMattersRestApi {

    @GET("items") @NonNull
    Single<List<Item>> items();
}
