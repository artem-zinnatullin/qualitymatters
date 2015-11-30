package com.artemzin.qualitymatters.functional_tests.tests;

import android.support.annotation.NonNull;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.artemzin.qualitymatters.R;
import com.artemzin.qualitymatters.ui.activities.MainActivity;
import com.artemzin.qualitymatters.functional_tests.rules.MockWebServerRule;
import com.artemzin.qualitymatters.functional_tests.rules.NeedsMockWebServer;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ItemsTest {

    @Rule
    public RuleChain rules = RuleChain.emptyRuleChain()
            .around(new MockWebServerRule(this))
            .around(new ActivityTestRule<>(MainActivity.class));

    @Test
    @NeedsMockWebServer(setupMethod = "load_shouldDisplayAllItemsSetupMockWebServer")
    public void load_shouldDisplayAllItems() {
        onView(withId(R.id.items_content_ui)).perform(actionOnItem(ViewMatchers.withText(Matchers.containsString("title 1")), ViewActions.click()));
    }

    /**
     * @see #load_shouldDisplayAllItems()
     */
    public void load_shouldDisplayAllItemsSetupMockWebServer(@NonNull MockWebServer mockWebServer) {
        mockWebServer.enqueue(new MockResponse().setBody("["
                + "{ \"id\": \"test_id_1\", \"image_preview_url\": \"https://url1\", \"title\": \"Test title 1\", \"short_description\": \"Short desc 1\"},"
                + "{ \"id\": \"test_id_2\", \"image_preview_url\": \"https://url2\", \"title\": \"Test title 2\", \"short_description\": \"Short desc 2\"},"
                + "{ \"id\": \"test_id_3\", \"image_preview_url\": \"https://url3\", \"title\": \"Test title 3\", \"short_description\": \"Short desc 3\"}"
                + "]"));
    }
}
