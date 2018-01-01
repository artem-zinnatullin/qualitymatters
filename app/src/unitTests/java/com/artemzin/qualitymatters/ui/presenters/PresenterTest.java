package com.artemzin.qualitymatters.ui.presenters;

import io.reactivex.disposables.Disposable;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.mockito.Mockito.*;

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

        Disposable subscription1 = mock(Disposable.class);
        Disposable subscription2 = mock(Disposable.class);
        Disposable subscription3 = mock(Disposable.class);

        presenter.unsubscribeOnUnbindView(subscription1, subscription2, subscription3);
        verify(subscription1, never()).dispose();
        verify(subscription2, never()).dispose();
        verify(subscription3, never()).dispose();

        presenter.unbindView(view);
        verify(subscription1).dispose();
        verify(subscription2).dispose();
        verify(subscription3).dispose();
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