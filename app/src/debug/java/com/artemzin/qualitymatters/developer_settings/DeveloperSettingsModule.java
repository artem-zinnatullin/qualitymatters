package com.artemzin.qualitymatters.developer_settings;

import android.app.Application;
import android.support.annotation.NonNull;

import com.artemzin.qualitymatters.analytics.AnalyticsModel;
import com.artemzin.qualitymatters.base.ui.ViewModifier;
import com.artemzin.qualitymatters.developer_settings.ui.DeveloperSettingsPresenter;
import com.github.pedrovgs.lynx.LynxConfig;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.supercluster.paperwork.Paperwork;
import okhttp3.logging.HttpLoggingInterceptor;

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
    public DeveloperSettings provideDeveloperSettings(@NonNull Application application) {
        return new DeveloperSettings(application.getSharedPreferences("developer_settings", MODE_PRIVATE));
    }

    @Provides
    @NonNull
    @Singleton
    public LeakCanaryProxy provideLeakCanaryProxy(@NonNull Application application) {
        return new LeakCanaryProxyImpl(application);
    }

    @Provides
    @NonNull
    @Singleton
    public Paperwork providePaperwork(@NonNull Application application) {
        return new Paperwork(application);
    }

    // We will use this concrete type for debug code, but main code will see only DeveloperSettingsModel interface.
    @Provides
    @NonNull
    @Singleton
    public DeveloperSettingsModelImpl provideDeveloperSettingsModelImpl(@NonNull Application application,
                                                                        @NonNull DeveloperSettings developerSettings,
                                                                        @NonNull HttpLoggingInterceptor httpLoggingInterceptor,
                                                                        @NonNull LeakCanaryProxy leakCanaryProxy,
                                                                        @NonNull Paperwork paperwork) {
        return new DeveloperSettingsModelImpl(application, developerSettings, httpLoggingInterceptor, leakCanaryProxy, paperwork);
    }

    @Provides
    @NonNull
    public DeveloperSettingsPresenter provideDeveloperSettingsPresenter(@NonNull DeveloperSettingsModelImpl developerSettingsModelImpl,
                                                                        @NonNull AnalyticsModel analyticsModel) {
        return new DeveloperSettingsPresenter(developerSettingsModelImpl, analyticsModel);
    }

    @NonNull
    @Provides
    public LynxConfig provideLynxConfig() {
        return new LynxConfig();
    }

    @NonNull
    @Provides
    @Singleton
    public DevMetricsProxy provideDevMetricsProxy(@NonNull Application application) {
        return new DevMetricsProxyImpl(application);
    }
}
