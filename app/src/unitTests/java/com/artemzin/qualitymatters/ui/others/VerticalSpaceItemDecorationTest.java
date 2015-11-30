package com.artemzin.qualitymatters.functional_tests.others;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

public class VerticalSpaceItemDecorationTest {

    @Test
    public void getItemOffsets_shouldSetRequiredSpaceInPxAsBottomToTheOuterRect() {
        VerticalSpaceItemDecoration verticalSpaceItemDecoration = new VerticalSpaceItemDecoration(10);
        Rect outRect = new Rect();
        View view = mock(View.class);
        RecyclerView recyclerView = mock(RecyclerView.class);
        RecyclerView.State state = mock(RecyclerView.State.class);

        verticalSpaceItemDecoration.getItemOffsets(outRect, view, recyclerView, state);
        assertThat(outRect.bottom).isEqualTo(10);
        assertThat(outRect.top).isEqualTo(10);
        assertThat(outRect.left).isEqualTo(0);
        assertThat(outRect.right).isEqualTo(0);

        // We don't expect any manipulations with passed params except outRect.
        verifyZeroInteractions(view, recyclerView, state);
    }
}