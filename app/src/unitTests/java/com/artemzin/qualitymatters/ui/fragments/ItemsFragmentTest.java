package com.artemzin.qualitymatters.ui.fragments;

import com.artemzin.qualitymatters.ui.presenters.ItemsPresenter;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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