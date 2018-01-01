package com.artemzin.qualitymatters.api;

import android.support.annotation.NonNull;
import com.artemzin.qualitymatters.api.entities.Item;
import io.reactivex.Single;
import retrofit2.http.GET;

import java.util.List;

public interface QualityMattersRestApi {

    @GET("items") @NonNull
    Single<List<Item>> items();
}
