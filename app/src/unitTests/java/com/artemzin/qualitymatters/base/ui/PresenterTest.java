package com.artemzin.qualitymatters.base.ui;

import org.junit.Before;
import org.junit.Test;

import rx.Subscription;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class PresenterTest {
    private Presenter<Object> presenter;
    private Object view;

    @Before
    public void beforeEachTest() {
        view = new Object();
        presenter = new Presenter<>();
    }

    @Test
    public void bindView_shouldAttachViewToThePresenter() {
        presenter.bindView(view);
        assertThat(presenter.view()).isSameAs(view);
    }

    @Test
    public void bindView_shouldThrowIfPreviousViewIsNotUnbounded() {
        presenter.bindView(view);

        try {
            presenter.bindView(new Object());
            failBecauseExceptionWasNotThrown(IllegalStateException.class);
        } catch (IllegalStateException expected) {
            assertThat(expected).hasMessage("Previous view is not unbounded! previousView = " + view);
        }
    }

    @Test
    public void view_shouldReturnNullByDefault() {
        assertThat(presenter.view()).isNull();
    }

    @Test
    public void unsubscribeOnUnbindView_shouldWorkAccordingItsContract() {
        presenter.bindView(view);

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

    @Test
    public void unbindView_shouldNullTheViewReference() {
        presenter.bindView(view);
        assertThat(presenter.view()).isSameAs(view);

        presenter.unbindView(view);
        assertThat(presenter.view()).isNull();
    }

    @Test
    public void unbindView_shouldThrowIfPreviousViewIsNotSameAsExpected() {
        presenter.bindView(view);
        Object unexpectedView = new Object();

        try {
            presenter.unbindView(unexpectedView);
            failBecauseExceptionWasNotThrown(IllegalStateException.class);
        } catch (IllegalStateException expected) {
            assertThat(expected).hasMessage("Unexpected view! previousView = " + view + ", view to unbind = " + unexpectedView);
        }
    }
}