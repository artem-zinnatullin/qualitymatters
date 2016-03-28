package com.artemzin.qualitymatters.models;

import android.support.annotation.NonNull;
import android.widget.ImageView;

public interface QualityMattersImageLoader {
    void downloadInto(@NonNull String url, @NonNull ImageView imageView);
}