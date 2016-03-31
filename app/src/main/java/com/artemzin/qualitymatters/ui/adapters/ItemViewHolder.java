package com.artemzin.qualitymatters.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.artemzin.qualitymatters.R;
import com.artemzin.qualitymatters.api.entities.Item;
import com.artemzin.qualitymatters.models.QualityMattersImageLoader;

class ItemViewHolder extends RecyclerView.ViewHolder {

    @NonNull
    private final QualityMattersImageLoader imageLoader;

    private final ImageView imageView;

    private final TextView titleTextView;

    private final TextView shortDescriptionTextView;

    ItemViewHolder(@NonNull View itemView, @NonNull QualityMattersImageLoader imageLoader) {
        super(itemView);
        this.imageLoader = imageLoader;
        imageView = (ImageView) itemView.findViewById(R.id.list_item_image_view);
        titleTextView = (TextView) itemView.findViewById(R.id.list_item_title_text_view);
        shortDescriptionTextView = (TextView) itemView.findViewById(R.id.list_item_short_description_text_view);
    }

    public void bind(@NonNull Item item) {
        imageLoader.downloadInto(item.imagePreviewUrl(), imageView);
        titleTextView.setText(item.title());
        shortDescriptionTextView.setText(item.shortDescription());
    }
}
