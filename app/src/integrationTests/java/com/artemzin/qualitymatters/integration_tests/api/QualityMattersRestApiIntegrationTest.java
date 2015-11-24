package com.artemzin.qualitymatters.integration_tests.api;

import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.QualityMattersRobolectricTestRunner;
import com.artemzin.qualitymatters.api.QualityMattersRestApi;
import com.artemzin.qualitymatters.api.entities.Item;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import retrofit.HttpException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * Main purpose of Integration tests is to check that all layers of your app work correctly, for example:
 * <ul>
 * <li>Http layer</li>
 * <li>REST layer</li>
 * <li>Parsing/Serializing layer</li>
 * <li>Execution layer (ie RxJava)</li>
 * </ul>
 */
@RunWith(QualityMattersRobolectricTestRunner.class)
public class QualityMattersRestApiIntegrationTest {

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private MockWebServer mockWebServer;

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private QualityMattersRestApi qualityMattersRestApi;

    @Before
    public void beforeEachTest() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        // Change base url to the mocked
        QualityMattersRobolectricTestRunner.qualityMattersApp().applicationComponent().changeableBaseUrl().setBaseUrl(mockWebServer.url("").toString());

        qualityMattersRestApi = QualityMattersRobolectricTestRunner.qualityMattersApp().applicationComponent().qualityMattersApi();
    }

    @After
    public void afterEachTest() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void items_shouldHandleCorrectResponse() {
        mockWebServer.enqueue(new MockResponse().setBody("["
                + "{ \"id\": \"test_id_1\", \"title\": \"Test title 1\", \"short_description\": \"Short desc 1\"},"
                + "{ \"id\": \"test_id_2\", \"title\": \"Test title 2\", \"short_description\": \"Short desc 2\"},"
                + "{ \"id\": \"test_id_3\", \"title\": \"Test title 3\", \"short_description\": \"Short desc 3\"}"
                + "]"));

        // Get items from the API
        List<Item> items = qualityMattersRestApi.items().toBlocking().value();

        assertThat(items).hasSize(3);

        assertThat(items.get(0).id()).isEqualTo("test_id_1");
        assertThat(items.get(0).title()).isEqualTo("Test title 1");
        assertThat(items.get(0).shortDescription()).isEqualTo("Short desc 1");

        assertThat(items.get(1).id()).isEqualTo("test_id_2");
        assertThat(items.get(1).title()).isEqualTo("Test title 2");
        assertThat(items.get(1).shortDescription()).isEqualTo("Short desc 2");

        assertThat(items.get(2).id()).isEqualTo("test_id_3");
        assertThat(items.get(2).title()).isEqualTo("Test title 3");
        assertThat(items.get(2).shortDescription()).isEqualTo("Short desc 3");
    }

    // Such tests assert that no matter how we implement our REST api:
    // Retrofit or not
    // OkHttp or not
    // It should handle error responses too.
    @Test
    public void items_shouldThrowExceptionIfWebServerRespondError() {
        for (Integer errorCode : HttpCodes.clientAndServerSideErrorCodes()) {
            mockWebServer.enqueue(new MockResponse().setStatus("HTTP/1.1 " + errorCode + " Not today"));

            try {
                qualityMattersRestApi.items().toBlocking().value();
                fail("HttpException should be thrown for error code: " + errorCode);
            } catch (RuntimeException expected) {
                HttpException httpException = (HttpException) expected.getCause();
                assertThat(httpException.code()).isEqualTo(errorCode);
                assertThat(httpException.message()).isEqualTo("Not today");
            }
        }
    }
}
