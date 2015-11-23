package com.artemzin.androiddevelopmentculture.api;

import com.artemzin.androiddevelopmentculture.api.entities.Item;

import java.util.List;

import retrofit.http.GET;
import rx.Single;

public interface ADCApi {

    @GET("/items")
    Single<List<Item>> items();
}
