package com.artemzin.androiddevelopmentculture.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artemzin.androiddevelopmentculture.R;
import com.artemzin.androiddevelopmentculture.api.entities.Item;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    @NonNull
    private final LayoutInflater layoutInflater;

    @NonNull
    private List<Item> items = emptyList();

    public ItemsAdapter(@NonNull LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setData(@NonNull List<Item> items) {
        this.items = unmodifiableList(items); // Prevent possible side-effects.
        notifyDataSetChanged();
    }

    @Override @NonNull
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(layoutInflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder viewHolder, int position) {
        viewHolder.bind(items.get(position));
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.list_item_image_view)
        ImageView imageView;

        @Bind(R.id.list_item_title_text_view)
        TextView titleTextView;

        @Bind(R.id.list_item_short_description_text_view)
        TextView shortDescriptionTextView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(@NonNull Item item) {
            titleTextView.setText(item.title());
            shortDescriptionTextView.setText(item.shortDescription());
        }
    }
}
