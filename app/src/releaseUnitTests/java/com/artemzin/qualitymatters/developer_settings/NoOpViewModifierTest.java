package com.artemzin.qualitymatters.developer_settings;

import android.view.View;

import com.artemzin.qualitymatters.ui.other.NoOpViewModifier;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

public class NoOpViewModifierTest {

    @Test
    public void modify_shouldReturnPassedViewAndNotInteractWithIt() {
        View view = mock(View.class);

        assertThat(new NoOpViewModifier().modify(view)).isSameAs(view);
        verifyZeroInteractions(view);
    }
}