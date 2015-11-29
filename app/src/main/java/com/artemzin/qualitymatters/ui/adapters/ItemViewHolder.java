package com.artemzin.qualitymatters.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.artemzin.qualitymatters.R;
import com.artemzin.qualitymatters.api.entities.Item;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

class ItemViewHolder extends RecyclerView.ViewHolder {

    @NonNull
    private final Picasso picasso;

    @Bind(R.id.list_item_image_view)
    ImageView imageView;

    @Bind(R.id.list_item_title_text_view)
    TextView titleTextView;

    @Bind(R.id.list_item_short_description_text_view)
    TextView shortDescriptionTextView;

    public ItemViewHolder(@NonNull View itemView, @NonNull Picasso picasso) {
        super(itemView);
        this.picasso = picasso;
        ButterKnife.bind(this, itemView);
    }

    public void bind(@NonNull Item item) {
        picasso.load(item.imagePreviewUrl()).fit().centerCrop().into(imageView);
        titleTextView.setText(item.title());
        shortDescriptionTextView.setText(item.shortDescription());
    }
}
