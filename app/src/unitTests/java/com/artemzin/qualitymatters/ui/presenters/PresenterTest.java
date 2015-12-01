package com.artemzin.qualitymatters.ui.presenters;

import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;

import rx.Subscription;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class PresenterTest {

    private static class PresenterTestImpl extends Presenter<Object> {

        @Override
        public void bindView(@NonNull Object view) {
            // no-op
        }
    }

    @NonNull
    private Presenter<Object> presenter;

    @NonNull
    private Object view;

    @Before
    public void beforeEachTest() {
        view = mock(Object.class);
        presenter = new PresenterTestImpl();
    }

    @Test
    public void unsubscribeOnUnbindView_shouldWorkAccordingItsContract() {
        Subscription subscription1 = mock(Subscription.class);
        Subscription subscription2 = mock(Subscription.class);
        Subscription subscription3 = mock(Subscription.class);

        presenter.unsubscribeOnUnbindView(subscription1, subscription2, subscription3);
        verify(subscription1, never()).unsubscribe();
        verify(subscription2, never()).unsubscribe();
        verify(subscription3, never()).unsubscribe();

        presenter.unbindView(view);
        verify(subscription1).unsubscribe();
        verify(subscription2).unsubscribe();
        verify(subscription3).unsubscribe();
    }
}