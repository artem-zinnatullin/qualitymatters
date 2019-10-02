package com.artemzin.qualitymatters;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.artemzin.qualitymatters.base.ui.BaseActivity;
import com.artemzin.qualitymatters.base.ui.ViewModifier;
import com.artemzin.qualitymatters.items.ui.ItemsFragment;

import javax.inject.Inject;
import javax.inject.Named;

import static com.artemzin.qualitymatters.developer_settings.DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER;

public class MainActivity extends BaseActivity {

    @Inject @Named(MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;

    @SuppressLint("InflateParams") // It's okay in our case.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QualityMattersApp.get(this).applicationComponent().inject(this);

        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));

        if (savedInstanceState == null) {
            // TODO switch to ScreenValley or Flow & Mortar
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, new ItemsFragment())
                    .commit();
        }
    }
}
