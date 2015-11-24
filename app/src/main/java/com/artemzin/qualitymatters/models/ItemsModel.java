package com.artemzin.qualitymatters.models;

import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.api.QualityMattersRestApi;
import com.artemzin.qualitymatters.api.entities.Item;

import java.util.List;

import javax.inject.Inject;

import rx.Single;

/**
 * Model is not an entity. It's a container for business logic code. M(VC), M(VP), M(VVM).
 * <p>
 * Why create Model classes? Such classes hide complex logic required to fetch/cache/process/etc data.
 * So Presentation layer won't know the details of implementation and each class will do only one job (SOLID).
 */
public class ItemsModel {

    @NonNull
    private final QualityMattersRestApi qualityMattersRestApi;

    @Inject
    public ItemsModel(@NonNull QualityMattersRestApi qualityMattersRestApi) {
        this.qualityMattersRestApi = qualityMattersRestApi;
    }

    @NonNull
    public Single<List<Item>> getItems() {
        return qualityMattersRestApi.items();
    }
}
