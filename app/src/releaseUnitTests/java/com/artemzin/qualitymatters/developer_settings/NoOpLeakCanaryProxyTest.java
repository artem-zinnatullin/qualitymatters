package com.artemzin.qualitymatters.developer_settings;

import org.junit.Test;

public class NoOpLeakCanaryProxyTest {

    @Test
    public void init_shouldNoOp() {
        new NoOpLeakCanaryProxy().init(); // No exceptions expected.
    }

    @Test
    public void watch_shouldNoOpIfWasNotInit() {
        new NoOpLeakCanaryProxy().watch(new Object()); // No exceptions expected.
    }

    @Test
    public void watch_shouldNoOpIfWasInit() {
        LeakCanaryProxy leakCanaryProxy = new NoOpLeakCanaryProxy();
        leakCanaryProxy.init();
        leakCanaryProxy.watch(new Object()); // No exceptions expected.
    }
}