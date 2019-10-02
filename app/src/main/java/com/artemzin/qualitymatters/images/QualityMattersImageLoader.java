package com.artemzin.qualitymatters.images;

import android.support.annotation.NonNull;
import android.widget.ImageView;

public interface QualityMattersImageLoader {
    void downloadInto(@NonNull String url, @NonNull ImageView imageView);
}