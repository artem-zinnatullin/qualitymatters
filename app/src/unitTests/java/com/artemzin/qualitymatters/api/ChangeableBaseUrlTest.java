package com.artemzin.qualitymatters.api;

import com.squareup.okhttp.HttpUrl;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ChangeableBaseUrlTest {

    @Test
    public void constructor_shouldUsePassedBaseUrl() {
        ChangeableBaseUrl changeableBaseUrl = new ChangeableBaseUrl("https://artemzin.com");
        assertThat(changeableBaseUrl.url()).isEqualTo(HttpUrl.parse("https://artemzin.com"));
    }

    @Test
    public void setBaseUrl() {
        ChangeableBaseUrl changeableBaseUrl = new ChangeableBaseUrl("https://1");
        changeableBaseUrl.setBaseUrl("https://2");
        assertThat(changeableBaseUrl.url()).isEqualTo(HttpUrl.parse("https://2"));
    }
}