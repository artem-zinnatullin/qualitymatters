package com.artemzin.qualitymatters.items.ui;

import com.artemzin.qualitymatters.QualityMattersRobolectricUnitTestRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(QualityMattersRobolectricUnitTestRunner.class)
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