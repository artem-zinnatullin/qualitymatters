package com.artemzin.qualitymatters.ui.others;

import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;

import com.artemzin.qualitymatters.QualityMattersApp;

public class TestUtils {

    private TestUtils() {
        throw new IllegalStateException("No instances, please");
    }

    @NonNull
    public static QualityMattersApp app() {
        return (QualityMattersApp) InstrumentationRegistry.getTargetContext().getApplicationContext();
    }
}
