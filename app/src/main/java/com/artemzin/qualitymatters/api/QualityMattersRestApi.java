package com.artemzin.qualitymatters.api;

import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.items.entities.Item;

import java.util.List;

import retrofit2.http.GET;
import rx.Single;

public interface QualityMattersRestApi {

    @GET("items") @NonNull
    Single<List<Item>> items();
}
