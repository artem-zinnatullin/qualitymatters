package com.artemzin.qualitymatters.developer_settings;

import com.artemzin.qualitymatters.QualityMattersApp;

import org.junit.Test;

import static org.mockito.Mockito.mock;

public class LeakCanaryProxyImplTest {

    // Unfortunately, we can not really test init method since launching LeakCanary in the tests is not a great idea.

    @Test
    public void watch_shouldNoOpIfInitWasNotCalledCaseWhenLeakCanaryDisabled() {
        LeakCanaryProxy leakCanaryProxy = new LeakCanaryProxyImpl(mock(QualityMattersApp.class));
        leakCanaryProxy.watch(new Object()); // No exceptions expected.
    }
}