package com.artemzin.qualitymatters.ui.presenters;

import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.api.entities.Item;
import com.artemzin.qualitymatters.models.ItemsModel;
import com.artemzin.qualitymatters.performance.AsyncJob;
import com.artemzin.qualitymatters.performance.AsyncJobsObserver;
import com.artemzin.qualitymatters.ui.views.ItemsView;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import rx.Single;
import rx.schedulers.Schedulers;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class ItemsPresenterTest {

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private ItemsModel itemsModel;

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private ItemsPresenterConfiguration presenterConfiguration;

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private ItemsPresenter itemsPresenter;

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private ItemsView itemsView;

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private AsyncJobsObserver asyncJobsObserver;

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private AsyncJob asyncJob;

    @Before
    public void beforeEachTest() {
        itemsModel = mock(ItemsModel.class);
        presenterConfiguration = ItemsPresenterConfiguration.builder()
                .ioScheduler(Schedulers.immediate()) // We don't need async behavior in tests.
                .build();

        asyncJobsObserver = mock(AsyncJobsObserver.class);
        asyncJob = mock(AsyncJob.class);
        when(asyncJobsObserver.asyncJobStarted("Reload data in ItemsPresenter")).thenReturn(asyncJob);

        itemsPresenter = new ItemsPresenter(presenterConfiguration, itemsModel, asyncJobsObserver);
        itemsView = mock(ItemsView.class);
    }

    @Test
    public void reloadData_shouldMoveViewToLoadingState() {
        itemsPresenter.bindView(itemsView);
        verifyZeroInteractions(itemsView, asyncJobsObserver);

        when(itemsModel.getItems()).thenReturn(Single.just(emptyList()));

        itemsPresenter.reloadData();
        verify(itemsView).showLoadingUi();

        verify(asyncJobsObserver).asyncJobStarted("Reload data in ItemsPresenter");
        verify(asyncJobsObserver).asyncJobFinished(asyncJob);
    }

    @Test
    public void reloadData_shouldSendDataToTheView() {
        itemsPresenter.bindView(itemsView);
        verifyZeroInteractions(itemsView, asyncJobsObserver);

        List<Item> items = asList(
                Item.builder().id("1").imagePreviewUrl("i1").title("t1").shortDescription("s1").build(),
                Item.builder().id("2").imagePreviewUrl("i2").title("t2").shortDescription("s2").build()
        );

        when(itemsModel.getItems()).thenReturn(Single.just(items));

        itemsPresenter.reloadData();
        verify(itemsView).showContentUi(items);
        verify(itemsView, never()).showErrorUi(any(Throwable.class));
        verify(asyncJobsObserver).asyncJobStarted("Reload data in ItemsPresenter");
        verify(asyncJobsObserver).asyncJobFinished(asyncJob);
    }

    @Test
    public void reloadData_shouldSendErrorToTheView() {
        itemsPresenter.bindView(itemsView);
        verifyZeroInteractions(itemsView, asyncJobsObserver);

        Throwable error = new RuntimeException();
        when(itemsModel.getItems()).thenReturn(Single.error(error));

        itemsPresenter.reloadData();
        verify(itemsView).showErrorUi(error);
        verify(itemsView, never()).showContentUi(anyListOf(Item.class));
        verify(asyncJobsObserver).asyncJobStarted("Reload data in ItemsPresenter");
        verify(asyncJobsObserver).asyncJobFinished(asyncJob);
    }
}