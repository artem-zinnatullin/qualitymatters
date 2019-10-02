package com.artemzin.qualitymatters.items.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artemzin.qualitymatters.QualityMattersApp;
import com.artemzin.qualitymatters.R;
import com.artemzin.qualitymatters.items.entities.Item;
import com.artemzin.qualitymatters.analytics.AnalyticsModel;
import com.artemzin.qualitymatters.items.ItemsModel;
import com.artemzin.qualitymatters.images.QualityMattersImageLoader;
import com.artemzin.qualitymatters.performance.AnyThread;
import com.artemzin.qualitymatters.performance.AsyncJobsObserver;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.artemzin.qualitymatters.base.ui.BaseFragment;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import rx.schedulers.Schedulers;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ItemsFragment extends BaseFragment implements ItemsView {
    @BindView(R.id.items_loading_ui)
    View loadingUiView;

    @BindView(R.id.items_loading_error_ui)
    View errorUiView;

    @BindView(R.id.items_content_ui)
    RecyclerView contentUiRecyclerView;

    ItemsAdapter itemsAdapter;

    @Inject
    ItemsPresenter itemsPresenter;

    @Inject
    QualityMattersImageLoader networkBitmapClient;

    @SuppressWarnings("NullableProblems")
    @NonNull
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QualityMattersApp.get(getContext()).applicationComponent().plus(new ItemsFragmentModule()).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        contentUiRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), VERTICAL, false));
        contentUiRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration((int) getResources().getDimension(R.dimen.list_item_vertical_space_between_items)));
        itemsAdapter = new ItemsAdapter(getActivity().getLayoutInflater(), networkBitmapClient);
        contentUiRecyclerView.setAdapter(itemsAdapter);
        itemsPresenter.bindView(this);
        itemsPresenter.reloadData();
    }

    @SuppressWarnings("ResourceType")
    // Lint does not understand that we shift execution on Main Thread.
    @Override
    @AnyThread
    public void showLoadingUi() {
        runOnUiThreadIfFragmentAlive(() -> {
            loadingUiView.setVisibility(VISIBLE);
            errorUiView.setVisibility(GONE);
            contentUiRecyclerView.setVisibility(GONE);
        });
    }

    @SuppressWarnings("ResourceType")
    // Lint does not understand that we shift execution on Main Thread.
    @Override
    @AnyThread
    public void showErrorUi(@NonNull Throwable error) {
        runOnUiThreadIfFragmentAlive(() -> {
            loadingUiView.setVisibility(GONE);
            errorUiView.setVisibility(VISIBLE);
            contentUiRecyclerView.setVisibility(GONE);
        });
    }

    @SuppressWarnings("ResourceType")
    // Lint does not understand that we shift execution on Main Thread.
    @Override
    @AnyThread
    public void showContentUi(@NonNull List<Item> items) {
        runOnUiThreadIfFragmentAlive(() -> {
            loadingUiView.setVisibility(GONE);
            errorUiView.setVisibility(GONE);
            contentUiRecyclerView.setVisibility(VISIBLE);

            if (itemsAdapter != null) {
                itemsAdapter.setData(items);
            }
        });
    }

    @OnClick(R.id.items_loading_error_try_again_button)
    void onTryAgainButtonClick() {
        itemsPresenter.reloadData();
    }

    @Override
    public void onDestroyView() {
        itemsPresenter.unbindView(this);

        if (unbinder != null) {
            unbinder.unbind();
        }

        super.onDestroyView();
    }

    @Subcomponent(modules = ItemsFragmentModule.class)
    public interface ItemsFragmentComponent {
        void inject(@NonNull ItemsFragment itemsFragment);
    }

    @Module
    public static class ItemsFragmentModule {

        @Provides
        @NonNull
        public ItemsPresenter provideItemsPresenter(@NonNull ItemsModel itemsModel,
                                                    @NonNull AsyncJobsObserver asyncJobsObserver,
                                                    @NonNull AnalyticsModel analyticsModel) {
            return new ItemsPresenter(
                    ItemsPresenterConfiguration.builder().ioScheduler(Schedulers.io()).build(),
                    itemsModel,
                    asyncJobsObserver,
                    analyticsModel
            );
        }
    }
}
