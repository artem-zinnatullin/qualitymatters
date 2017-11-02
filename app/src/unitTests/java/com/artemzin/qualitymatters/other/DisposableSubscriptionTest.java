package com.artemzin.qualitymatters.other;

import io.reactivex.functions.Action;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DisposableSubscriptionTest {

    private Action disposeAction;
    private DisposableSubscription disposableSubscription;

    @Before
    public void beforeEachTest() {
        disposeAction = mock(Action.class);
        disposableSubscription = new DisposableSubscription(disposeAction);
    }

    @Test
    public void disposed_shouldReturnFalseByDefault() {
        assertThat(disposableSubscription.isDisposed()).isFalse();
        verifyZeroInteractions(disposeAction);
    }

    @Test
    public void dispose_shouldChangeValueOfIsDisposed() {
        disposableSubscription.dispose();
        assertThat(disposableSubscription.isDisposed()).isTrue();
    }

    @Test
    public void dispose_shouldCallDisposableAction() throws Exception {
        disposableSubscription.dispose();
        verify(disposeAction).run();
    }

    @Test
    public void disposeTwice_shouldCallDisposableActionOnce() throws Exception {
        disposableSubscription.dispose();
        disposableSubscription.dispose();
        verify(disposeAction, times(1)).run();
    }
}