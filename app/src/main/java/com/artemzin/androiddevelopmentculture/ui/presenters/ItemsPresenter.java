package com.artemzin.androiddevelopmentculture.ui.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artemzin.androiddevelopmentculture.models.ItemsModel;
import com.artemzin.androiddevelopmentculture.ui.views.ItemsView;

import javax.inject.Inject;

import rx.Subscription;

public class ItemsPresenter extends Presenter<ItemsView> {

    @NonNull
    private final ItemsPresenterConfiguration presenterConfiguration;

    @NonNull
    private final ItemsModel itemsModel;

    @Nullable
    private volatile ItemsView itemsView;

    @Inject
    public ItemsPresenter(@NonNull ItemsPresenterConfiguration presenterConfiguration, @NonNull ItemsModel itemsModel) {
        this.presenterConfiguration = presenterConfiguration;
        this.itemsModel = itemsModel;
    }

    @Override
    public void bindView(@NonNull ItemsView view) {
        this.itemsView = view;
    }

    public void reloadData() {
        {
            // Tip: in Kotlin you can use ? to operate with nullable values.
            final ItemsView view = itemsView;

            if (view != null) {
                view.showLoadingUi();
            }
        }

        final Subscription subscription = itemsModel
                .getItems()
                .subscribeOn(presenterConfiguration.ioScheduler())
                .subscribe(
                        items -> {
                            // Tip: in Kotlin you can use ? to operate with nullable values.
                            final ItemsView view = itemsView;

                            if (view != null) {
                                view.showContentUi(items);
                            }
                        },
                        error -> {
                            // Tip: in Kotlin you can use ? to operate with nullable values.
                            final ItemsView view = itemsView;

                            if (view != null) {
                                view.showErrorUi(error);
                            }
                        }
                );

        // Prevent memory leak.
        unsubscribeOnUnbindView(subscription);
    }
}
