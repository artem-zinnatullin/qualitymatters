package com.artemzin.qualitymatters.functional_tests.screens;

import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.artemzin.qualitymatters.functional_tests.espresso.ViewActions.noOp;
import static com.artemzin.qualitymatters.functional_tests.espresso.ViewAssertions.recyclerViewShouldHaveItemsCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

public class ItemsScreen {

    @NonNull
    public ItemsScreen shouldDisplayTitle(@NonNull String title) {
        onView(allOf(withText(title), withParent(withId(R.id.toolbar)))).check(matches(isDisplayed()));
        return this;
    }

    @NonNull
    public ItemsScreen shouldDisplayItemWithTitle(@NonNull String title) {
        onView(withId(R.id.items_content_ui))
                .perform(actionOnItem(hasDescendant(
                        allOf(
                                withId(R.id.list_item_title_text_view),
                                withText(title))),
                        noOp()));

        return this;
    }

    @NonNull
    public ItemsScreen shouldDisplayItemWithShortDescription(@NonNull String shortDescription) {
        onView(withId(R.id.items_content_ui))
                .perform(actionOnItem(hasDescendant(
                        allOf(
                                withId(R.id.list_item_short_description_text_view),
                                withText(shortDescription))),
                        noOp()));

        return this;
    }

    @NonNull
    public ItemsScreen shouldDisplayErrorText() {
        onView(withText(R.string.items_loading_error_text)).check(matches(isDisplayed()));
        return this;
    }

    @NonNull
    public ItemsScreen shouldDisplayTryAgainButton() {
        onView(withId(R.id.items_loading_error_try_again_button)).check(matches(isDisplayed()));
        return this;
    }

    @NonNull
    public ItemsScreen clickOnTryAgainButton() {
        onView(withId(R.id.items_loading_error_try_again_button)).perform(click());
        return this;
    }

    @NonNull
    public ItemsScreen shouldDisplayExpectedAmountOfItems(int itemsCount) {
        onView(withId(R.id.items_content_ui)).check(recyclerViewShouldHaveItemsCount(itemsCount));
        return this;
    }

    @NonNull
    public ItemsScreen shouldNotDisplayItems() {
        onView(withId(R.id.items_content_ui)).check(matches(not(isDisplayed())));
        return this;
    }
}
