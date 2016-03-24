package com.artemzin.qualitymatters.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.IOException;
import java.net.MalformedURLException;
import okhttp3.Connection;
import okhttp3.HttpUrl;
import okhttp3.Interceptor.Chain;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class HostSelectionInterceptorTest {

    private static final String BASE_URL = "https://www.example.com:27015";
    private static final String CHANGED_URL = "http://localhost:27016";

    @NonNull
    private final HostSelectionInterceptor interceptor = spy(new HostSelectionInterceptor());

    @Test
    public void shouldNotHaveBaseUrlAfterCreation() {
        assertThat(interceptor.getBaseUrl()).isNull();
    }

    @Test
    public void shouldStoreBaseUrl() {
        interceptor.setBaseUrl(BASE_URL);
        assertThat(interceptor.getBaseUrl()).isEqualTo(BASE_URL);
    }

    @Test
    public void shouldNotModifyRequestsWithoutBaseUrl() throws IOException {
        interceptor.intercept(new TestChain(BASE_URL));
        verify(interceptor, never()).modifyRequest(any(), any());
    }

    @Test
    public void shouldModifyRequestsWithBaseUrl() throws IOException {
        interceptor.setBaseUrl(CHANGED_URL);
        interceptor.intercept(new TestChain(BASE_URL));
        verify(interceptor).modifyRequest(any(), any());
    }

    @Test
    public void canModifyRequest() throws MalformedURLException {
        final Request request = interceptor.modifyRequest(TestChain.BASE_REQUEST, CHANGED_URL);
        assertThat(request.isHttps()).isFalse();
        assertThat(request.url().host()).isEqualTo("localhost");
        assertThat(request.url().port()).isEqualTo(27016);
    }

    static class TestChain implements Chain {

        static final Request BASE_REQUEST = (new Request.Builder().url(HttpUrl.parse(BASE_URL))).build();

        @NonNull
        private final String baseUrl;

        TestChain(@NonNull String baseUrl) {
            this.baseUrl = baseUrl;
        }

        @Override
        public Request request() {
            return (new Request.Builder().url(HttpUrl.parse(baseUrl))).build();
        }

        @Override @NonNull
        public Response proceed(@NonNull Request request) throws IOException {
            return new Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .addHeader("Test header", "Test value")
                .build();
        }

        @Override @Nullable
        public Connection connection() {
            return null;
        }
    }
}