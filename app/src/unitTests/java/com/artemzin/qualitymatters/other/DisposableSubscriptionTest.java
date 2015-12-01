package com.artemzin.qualitymatters.other;

import org.junit.Before;
import org.junit.Test;

import rx.functions.Action0;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class DisposableSubscriptionTest {

    private Action0 disposeAction;
    private DisposableSubscription disposableSubscription;

    @Before
    public void beforeEachTest() {
        disposeAction = mock(Action0.class);
        disposableSubscription = new DisposableSubscription(disposeAction);
    }

    @Test
    public void unsubscribed_shouldReturnFalseByDefault() {
        assertThat(disposableSubscription.isUnsubscribed()).isFalse();
        verifyZeroInteractions(disposeAction);
    }

    @Test
    public void unsubscribe_shouldChangeValueOfIsUsubscribed() {
        disposableSubscription.unsubscribe();
        assertThat(disposableSubscription.isUnsubscribed()).isTrue();
    }

    @Test
    public void unsubscribe_shouldCallDisposableAction() {
        disposableSubscription.unsubscribe();
        verify(disposeAction).call();
    }
}