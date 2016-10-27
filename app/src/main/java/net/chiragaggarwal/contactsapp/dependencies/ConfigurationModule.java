package net.chiragaggarwal.contactsapp.dependencies;

import android.content.Context;
import android.support.annotation.VisibleForTesting;

import net.chiragaggarwal.contactsapp.R;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ConfigurationModule {
    private String baseUrl;

    @SuppressWarnings("unused")
    public ConfigurationModule() {
    }

    @VisibleForTesting
    public ConfigurationModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    @Singleton
    public Configuration providesEnvironmentConfiguration(Context context) {
        String baseUrl = this.baseUrl == null ? context.getString(R.string.base_url) : this.baseUrl;
        return new Configuration(baseUrl);
    }
}
