package com.artemzin.qualitymatters.models;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.artemzin.qualitymatters.QualityMattersRobolectricUnitTestRunner;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(QualityMattersRobolectricUnitTestRunner.class)
public class PicassoImageLoaderTest {

    private static final String FAKE_URL = "http://fakeUrl.com";

    private ImageView imageView;
    private Picasso picasso;
    private PicassoImageLoader picassoBitmapClient;
    private RequestCreator loadRequestCreator;
    private RequestCreator fitRequestCreator;
    private RequestCreator centerCropRequestCreator;

    @Before
    public void setUp() {
        picasso = mock(Picasso.class);
        imageView = mock(ImageView.class);

        loadRequestCreator = mock(RequestCreator.class);
        fitRequestCreator = mock(RequestCreator.class);
        centerCropRequestCreator = mock(RequestCreator.class);

        when(picasso.load(FAKE_URL)).thenReturn(loadRequestCreator);
        when(loadRequestCreator.fit()).thenReturn(fitRequestCreator);
        when(fitRequestCreator.centerCrop()).thenReturn(centerCropRequestCreator);

        picassoBitmapClient = new PicassoImageLoader(picasso);
    }

    @Test
    public void downloadInto_shouldLoadThenFitThenCenterCrop() {
        picassoBitmapClient.downloadInto(FAKE_URL, imageView);

        verify(picasso).load(FAKE_URL);
        verify(loadRequestCreator).fit();
        verify(fitRequestCreator).centerCrop();
        verify(centerCropRequestCreator).into(imageView);
    }
}