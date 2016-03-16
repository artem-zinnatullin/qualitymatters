package com.artemzin.qualitymatters.developer_settings;

import android.app.Application;
import android.support.annotation.NonNull;

import com.frogermcs.androiddevmetrics.AndroidDevMetrics;

public class DevMetricsProxyImpl implements DevMetricsProxy {

  @NonNull
  private final Application application;

  public DevMetricsProxyImpl(@NonNull final Application application) {
    this.application = application;
  }

  @Override
  public void apply() {
    AndroidDevMetrics.initWith(application);
  }
}
