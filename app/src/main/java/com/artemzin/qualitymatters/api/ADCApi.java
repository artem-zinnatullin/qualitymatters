package com.artemzin.qualitymatters.api;

import com.artemzin.qualitymatters.api.entities.Item;

import java.util.List;

import retrofit.http.GET;
import rx.Single;

public interface ADCApi {

    @GET("/items")
    Single<List<Item>> items();
}
