package com.artemzin.qualitymatters.developer_settings;

public class NoOpDevMetricsProxy implements DevMetricsProxy {
    @Override
    public void apply() {
        // no-op.
    }
}
