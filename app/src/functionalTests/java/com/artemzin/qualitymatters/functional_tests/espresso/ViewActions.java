package com.artemzin.qualitymatters.functional_tests.espresso;

import android.support.annotation.NonNull;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import org.hamcrest.Matcher;

public class ViewActions {

    private ViewActions() {
        throw new IllegalStateException("No instances please");
    }

    @NonNull
    public static ViewAction noOp() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "no-op";
            }

            @Override
            public void perform(UiController uiController, View view) {
                // no-op
            }
        };
    }
}
