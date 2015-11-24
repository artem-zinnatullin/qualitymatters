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
        Subscription subscription = mock(Subscription.class);

        presenter.unsubscribeOnUnbindView(subscription);
        verify(subscription, never()).unsubscribe();

        presenter.unbindView(view);
        verify(subscription).unsubscribe();
    }
}