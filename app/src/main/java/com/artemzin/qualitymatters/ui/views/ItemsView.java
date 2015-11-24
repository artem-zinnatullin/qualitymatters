package com.artemzin.qualitymatters.ui.views;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import com.artemzin.qualitymatters.api.entities.Item;

import java.util.List;

/**
 * Main purpose of such interfaces — hide details of View implementation,
 * such as hundred methods of {@link android.support.v4.app.Fragment}.
 */
public interface ItemsView {

    // Presenter should not know about Main Thread. It's a detail of View implementation.
    @WorkerThread
    void showLoadingUi();

    @WorkerThread
    void showErrorUi(@NonNull Throwable error);

    @WorkerThread
    void showContentUi(@NonNull List<Item> items);
}
