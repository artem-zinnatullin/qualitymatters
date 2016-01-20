package com.artemzin.qualitymatters.developer_settings;

import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.QualityMattersApp;
import com.artemzin.qualitymatters.models.AnalyticsModel;
import com.artemzin.qualitymatters.ui.other.ViewModifier;
import com.artemzin.qualitymatters.ui.presenters.DeveloperSettingsPresenter;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.supercluster.paperwork.Paperwork;

import static android.content.Context.MODE_PRIVATE;

@Module
public class DeveloperSettingsModule {

    @NonNull
    public static final String MAIN_ACTIVITY_VIEW_MODIFIER = "main_activity_view_modifier";

    @Provides
    @NonNull
    @Named(MAIN_ACTIVITY_VIEW_MODIFIER)
    public ViewModifier provideMainActivityViewModifier() {
        return new MainActivityViewModifier();
    }

    @Provides
    @NonNull
    public DeveloperSettingsModel provideDeveloperSettingsModel(@NonNull DeveloperSettingsModelImpl developerSettingsModelImpl) {
        return developerSettingsModelImpl;
    }

    @Provides
    @NonNull
    @Singleton
    public DeveloperSettings provideDeveloperSettings(@NonNull QualityMattersApp qualityMattersApp) {
        return new DeveloperSettings(qualityMattersApp.getSharedPreferences("developer_settings", MODE_PRIVATE));
    }

    @Provides
    @NonNull
    @Singleton
    public LeakCanaryProxy provideLeakCanaryProxy(@NonNull QualityMattersApp qualityMattersApp) {
        return new LeakCanaryProxyImpl(qualityMattersApp);
    }

    @Provides
    @NonNull
    @Singleton
    public Paperwork providePaperwork(@NonNull QualityMattersApp qualityMattersApp) {
        return new Paperwork(qualityMattersApp);
    }

    // We will use this concrete type for debug code, but main code will see only DeveloperSettingsModel interface.
    @Provides
    @NonNull
    @Singleton
    public DeveloperSettingsModelImpl provideDeveloperSettingsModelImpl(@NonNull QualityMattersApp qualityMattersApp,
                                                                        @NonNull DeveloperSettings developerSettings,
                                                                        @NonNull OkHttpClient okHttpClient,
                                                                        @NonNull HttpLoggingInterceptor httpLoggingInterceptor,
                                                                        @NonNull LeakCanaryProxy leakCanaryProxy,
                                                                        @NonNull Paperwork paperwork) {
        return new DeveloperSettingsModelImpl(qualityMattersApp, developerSettings, okHttpClient, httpLoggingInterceptor, leakCanaryProxy, paperwork);
    }

    @Provides
    @NonNull
    public DeveloperSettingsPresenter provideDeveloperSettingsPresenter(@NonNull DeveloperSettingsModelImpl developerSettingsModelImpl,
                                                                        @NonNull AnalyticsModel analyticsModel) {
        return new DeveloperSettingsPresenter(developerSettingsModelImpl, analyticsModel);
    }
}
