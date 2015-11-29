package com.artemzin.qualitymatters.ui.others;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int verticalSpacePx;

    public VerticalSpaceItemDecoration(int verticalSpacePx) {
        this.verticalSpacePx = verticalSpacePx;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom = verticalSpacePx;
    }
}
