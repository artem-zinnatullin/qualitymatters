package com.artemzin.qualitymatters.ui.adapters;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.artemzin.qualitymatters.R;
import com.artemzin.qualitymatters.api.entities.Item;
import com.artemzin.qualitymatters.models.QualityMattersImageLoader;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ItemViewHolderTest {
    private QualityMattersImageLoader imageLoader;
    private View itemView;
    private ItemViewHolder itemViewHolder;
    private ImageView imageView;
    private TextView titleTextView;
    private TextView shortDescriptionTextView;
    private Item item;

    @Before
    public void beforeEachTest() {
        imageLoader = mock(QualityMattersImageLoader.class);

        itemView = mock(View.class);

        imageView = mock(ImageView.class);
        titleTextView = mock(TextView.class);
        shortDescriptionTextView = mock(TextView.class);

        when(itemView.findViewById(R.id.list_item_image_view)).thenReturn(imageView);
        when(itemView.findViewById(R.id.list_item_title_text_view)).thenReturn(titleTextView);
        when(itemView.findViewById(R.id.list_item_short_description_text_view)).thenReturn(shortDescriptionTextView);

        itemViewHolder = new ItemViewHolder(itemView, imageLoader);

        item = Item.builder()
                .id("1")
                .imagePreviewUrl("https://testurl")
                .title("Test title")
                .shortDescription("Desc")
                .build();
    }

    @Test
    public void bind_shouldLoadPreviewImage() {
        itemViewHolder.bind(item);
        verify(imageLoader).downloadInto(item.imagePreviewUrl(), imageView);
    }

    @Test
    public void bind_shouldSetTitle() {
        itemViewHolder.bind(item);
        verify(titleTextView).setText(item.title());
    }

    @Test
    public void bind_shouldSetShortDescription() {
        itemViewHolder.bind(item);
        verify(shortDescriptionTextView).setText(item.shortDescription());
    }
}