package com.artemzin.qualitymatters.developer_settings;

import android.support.annotation.NonNull;

/**
 * Tiny interface to hide LeakCanary from main source set.
 * Yep LeakCanary has it's own no-op impl, but
 * this approach is universal and applicable to any libraries you want to
 * use in debug builds but not in release. Also, this interface is tinier than LeakCanary's no-op one.
 */
public interface LeakCanaryProxy {
    void init();
    void watch(@NonNull Object object);
}
