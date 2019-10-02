package com.artemzin.qualitymatters.base.ui;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Simple function that modifies a {@link View} and returns modified one so the consumer should use modifier version.
 */
public interface ViewModifier {

    @NonNull
    <T extends View> T modify(@NonNull T view);
}
