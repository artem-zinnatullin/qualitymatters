package com.artemzin.qualitymatters.ui.adapters;

import android.annotation.SuppressLint;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artemzin.qualitymatters.QualityMattersRobolectricUnitTestRunner;
import com.artemzin.qualitymatters.R;
import com.artemzin.qualitymatters.developer_settings.ui.DeveloperSettingsSpinnerAdapter;
import com.artemzin.qualitymatters.developer_settings.ui.DeveloperSettingsSpinnerAdapter.SelectionOption;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(QualityMattersRobolectricUnitTestRunner.class)
public class DeveloperSettingsSpinnerAdapterTest {

    private LayoutInflater layoutInflater;
    private DeveloperSettingsSpinnerAdapter<SelectionOption> adapter;

    @Before
    public void beforeEachTest() {
        layoutInflater = mock(LayoutInflater.class);
        adapter = new DeveloperSettingsSpinnerAdapter<>(layoutInflater);
    }

    @Test
    public void setSelectableOptions_shouldReturnSameAdapter() {
        assertThat(adapter.setSelectionOptions(emptyList())).isSameAs(adapter);
    }

    @Test
    public void setSelectableOptions_shouldNotifyObservers() {
        DataSetObserver dataSetObserver = mock(DataSetObserver.class);
        adapter.registerDataSetObserver(dataSetObserver);
        verifyZeroInteractions(dataSetObserver);

        adapter.setSelectionOptions(emptyList());
        verify(dataSetObserver).onChanged();
    }

    @Test
    public void getCount_shouldReturn0ByDefault() {
        assertThat(adapter.getCount()).isEqualTo(0);
    }

    @Test
    public void getCount_shouldReturn0FromEmptyList() {
        adapter.setSelectionOptions(emptyList());
        assertThat(adapter.getCount()).isEqualTo(0);
    }

    @Test
    public void getCount_shouldReturnCountFromList() {
        List<SelectionOption> selectionOptions = new ArrayList<>();
        selectionOptions.add(mock(SelectionOption.class));
        selectionOptions.add(mock(SelectionOption.class));
        selectionOptions.add(mock(SelectionOption.class));

        adapter.setSelectionOptions(selectionOptions);
        assertThat(adapter.getCount()).isEqualTo(3);
    }

    @Test
    public void getItem_shouldReturnItemsFromList() {
        List<SelectionOption> selectionOptions = new ArrayList<>();
        selectionOptions.add(mock(SelectionOption.class));
        selectionOptions.add(mock(SelectionOption.class));
        selectionOptions.add(mock(SelectionOption.class));

        adapter.setSelectionOptions(selectionOptions);

        assertThat(adapter.getItem(0)).isEqualTo(selectionOptions.get(0));
        assertThat(adapter.getItem(1)).isEqualTo(selectionOptions.get(1));
        assertThat(adapter.getItem(2)).isEqualTo(selectionOptions.get(2));
    }

    @Test
    public void getItemId_shouldReturnPositions() {
        List<SelectionOption> selectionOptions = asList(
                mock(SelectionOption.class),
                mock(SelectionOption.class),
                mock(SelectionOption.class)
        );

        adapter.setSelectionOptions(selectionOptions);

        assertThat(adapter.getItemId(0)).isEqualTo(0);
        assertThat(adapter.getItemId(1)).isEqualTo(1);
        assertThat(adapter.getItemId(2)).isEqualTo(2);
    }

    @SuppressLint("SetTextI18n")
    @Test
    public void getView_shouldBindDataAndReturnViewWithoutConvertView() {
        List<SelectionOption> selectionOptions = asList(
                mock(SelectionOption.class),
                mock(SelectionOption.class),
                mock(SelectionOption.class)
        );

        adapter.setSelectionOptions(selectionOptions);
        ViewGroup container = mock(ViewGroup.class);

        for (int position = 0; position < selectionOptions.size(); position++) {
            when(selectionOptions.get(position).title()).thenReturn("Title " + position);

            View view = mock(View.class);
            when(layoutInflater.inflate(R.layout.list_developer_settings_spinner_item, container, false)).thenReturn(view);

            TextView titleTextView = mock(TextView.class);
            when(view.findViewById(R.id.list_developer_settings_spinner_item_title_text_view)).thenReturn(titleTextView);

            // Notice: there is NO convertView, that what we want to check.
            assertThat(adapter.getView(position, null, container)).isSameAs(view);
            verify(titleTextView).setText("Title " + position);
        }
    }

    @Test
    public void getView_shouldBindDataAndReturnViewWithConvertView() {
        List<SelectionOption> selectionOptions = asList(
                mock(SelectionOption.class),
                mock(SelectionOption.class),
                mock(SelectionOption.class)
        );

        adapter.setSelectionOptions(selectionOptions);
        ViewGroup container = mock(ViewGroup.class);

        for (int position = 0; position < selectionOptions.size(); position++) {
            View view = mock(View.class);
            when(layoutInflater.inflate(R.layout.list_developer_settings_spinner_item, container, false)).thenReturn(view);

            DeveloperSettingsSpinnerAdapter.ViewHolder viewHolder = mock(DeveloperSettingsSpinnerAdapter.ViewHolder.class);
            when(view.getTag()).thenReturn(viewHolder);

            // Notice: there IS convertView, that what we want to check.
            assertThat(adapter.getView(position, view, container)).isSameAs(view);
            verify(viewHolder).bindItem(selectionOptions.get(position));
        }
    }

    @SuppressLint("SetTextI18n")
    @Test
    public void getDropDownView_shouldBindDataAndReturnViewWithoutConvertView() {
        List<SelectionOption> selectionOptions = asList(
                mock(SelectionOption.class),
                mock(SelectionOption.class),
                mock(SelectionOption.class)
        );

        adapter.setSelectionOptions(selectionOptions);
        ViewGroup container = mock(ViewGroup.class);

        for (int position = 0; position < selectionOptions.size(); position++) {
            when(selectionOptions.get(position).title()).thenReturn("Title " + position);

            View view = mock(View.class);
            when(layoutInflater.inflate(R.layout.list_developer_settings_spinner_drop_down_item, container, false)).thenReturn(view);

            TextView titleTextView = mock(TextView.class);
            when(view.findViewById(R.id.list_developer_settings_spinner_item_title_text_view)).thenReturn(titleTextView);

            // Notice: there is NO convertView, that what we want to check.
            assertThat(adapter.getDropDownView(position, null, container)).isSameAs(view);
            verify(titleTextView).setText("Title " + position);
        }
    }

    @Test
    public void getDropDownView_shouldBindDataAndReturnViewWithConvertView() {
        List<SelectionOption> selectionOptions = asList(
                mock(SelectionOption.class),
                mock(SelectionOption.class),
                mock(SelectionOption.class)
        );

        adapter.setSelectionOptions(selectionOptions);
        ViewGroup container = mock(ViewGroup.class);

        for (int position = 0; position < selectionOptions.size(); position++) {
            View view = mock(View.class);
            when(layoutInflater.inflate(R.layout.list_developer_settings_spinner_item, container, false)).thenReturn(view);

            DeveloperSettingsSpinnerAdapter.ViewHolder viewHolder = mock(DeveloperSettingsSpinnerAdapter.ViewHolder.class);
            when(view.getTag()).thenReturn(viewHolder);

            // Notice: there IS convertView, that what we want to check.
            assertThat(adapter.getDropDownView(position, view, container)).isSameAs(view);
            verify(viewHolder).bindItem(selectionOptions.get(position));
        }
    }
}