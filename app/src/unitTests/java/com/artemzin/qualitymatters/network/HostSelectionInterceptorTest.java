package com.artemzin.qualitymatters.network;

import junit.framework.Assert;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.internal.http.RealInterceptorChain;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;

public class HostSelectionInterceptorTest {

    public static final String TEST_URL = "https://google.com/api/clients";
    public static final String TEST_URL_2 = "https://yahoo.com/api/clients";
    public static final String TEST_HOST = "https://yahoo.com/";
    private HostSelectionInterceptor interceptor;
    private Interceptor.Chain chain;

    @Before
    public void beforeEachTest() {
        interceptor = new HostSelectionInterceptor();
        Request request = new Request.Builder().url(TEST_URL).build();
        chain = new RealInterceptorChain(Collections.emptyList(), null, null, null,0,
                request, null, null, 10000,
                10000, 10000);
//        Mockito.when(chain.request()).thenReturn(request);
    }

    @Test
    public void hostChanged() throws IOException {
//        interceptor.intercept(chain);
//        Assert.assertEquals(TEST_URL, chain.request().url().toString());
        interceptor.setHost(HttpUrl.parse(TEST_HOST));
        interceptor.intercept(chain);
        Assert.assertEquals(TEST_URL_2, chain.request().url().toString());
    }

}