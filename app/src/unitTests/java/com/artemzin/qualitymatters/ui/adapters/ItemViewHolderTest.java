package com.artemzin.qualitymatters.ui.adapters;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.artemzin.qualitymatters.QualityMattersRobolectricUnitTestRunner;
import com.artemzin.qualitymatters.R;
import com.artemzin.qualitymatters.api.entities.Item;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(QualityMattersRobolectricUnitTestRunner.class)
public class ItemViewHolderTest {

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private Picasso picasso;

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private View itemView;

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private ItemViewHolder itemViewHolder;

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private ImageView imageView;

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private TextView titleTextView;

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private TextView shortDescriptionTextView;

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private Item item;

    @Before
    public void beforeEachTest() {
        picasso = mock(Picasso.class);

        // Just mock the "Fluent API"…
        RequestCreator requestCreator = mock(RequestCreator.class);
        when(picasso.load(anyString())).thenReturn(requestCreator);
        when(requestCreator.fit()).thenReturn(requestCreator);
        when(requestCreator.centerCrop()).thenReturn(requestCreator);

        itemView = mock(View.class);

        imageView = mock(ImageView.class);
        titleTextView = mock(TextView.class);
        shortDescriptionTextView = mock(TextView.class);

        when(itemView.findViewById(R.id.list_item_image_view)).thenReturn(imageView);
        when(itemView.findViewById(R.id.list_item_title_text_view)).thenReturn(titleTextView);
        when(itemView.findViewById(R.id.list_item_short_description_text_view)).thenReturn(shortDescriptionTextView);

        itemViewHolder = new ItemViewHolder(itemView, picasso);

        item = Item.builder()
                .id("1")
                .imagePreviewUrl("https://testurl")
                .title("Test title")
                .shortDescription("Desc")
                .build();
    }

    @Test
    public void bind_shouldLoadPreviewImage() {
        RequestCreator loadRequestCreator = mock(RequestCreator.class);
        RequestCreator fitRequestCreator = mock(RequestCreator.class);
        RequestCreator centerCropRequestCreator = mock(RequestCreator.class);

        when(picasso.load(item.imagePreviewUrl())).thenReturn(loadRequestCreator);
        when(loadRequestCreator.fit()).thenReturn(fitRequestCreator);
        when(fitRequestCreator.centerCrop()).thenReturn(centerCropRequestCreator);

        itemViewHolder.bind(item);

        verify(picasso).load(item.imagePreviewUrl());
        verify(loadRequestCreator).fit();
        verify(fitRequestCreator).centerCrop();
        verify(centerCropRequestCreator).into(imageView);
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