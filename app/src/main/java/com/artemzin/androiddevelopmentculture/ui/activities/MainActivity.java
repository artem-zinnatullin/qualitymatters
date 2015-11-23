package com.artemzin.androiddevelopmentculture.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.artemzin.androiddevelopmentculture.R;
import com.artemzin.androiddevelopmentculture.ui.fragments.ItemsFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            // TODO switch to ScreenValley or Flow & Mortar
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, new ItemsFragment())
                    .commit();
        }
    }
}
