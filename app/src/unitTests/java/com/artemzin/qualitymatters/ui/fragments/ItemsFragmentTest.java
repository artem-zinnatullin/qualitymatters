package com.artemzin.qualitymatters.ui.fragments;

import com.artemzin.qualitymatters.QualityMattersRobolectricTestRunner;
import com.artemzin.qualitymatters.ui.presenters.ItemsPresenter;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(QualityMattersRobolectricTestRunner.class)
public class ItemsFragmentTest {

    @Test
    public void onTryAgainButtonClick_shouldRequestDataFromPresenter() {
        ItemsFragment itemsFragment = new ItemsFragment();
        ItemsPresenter itemsPresenter = mock(ItemsPresenter.class);

        itemsFragment.itemsPresenter = itemsPresenter;
        itemsFragment.onTryAgainButtonClick();
        verify(itemsPresenter).reloadData();
    }
}