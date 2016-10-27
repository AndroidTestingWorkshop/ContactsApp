package net.chiragaggarwal.contactsapp.dependencies;

import android.content.Context;

import net.chiragaggarwal.contactsapp.ContactsNetworkService;
import net.chiragaggarwal.contactsapp.ContactsService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    @Provides
    @Singleton
    public Retrofit providesRetrofit(Configuration configuration) {
        return new Retrofit.Builder()
                .baseUrl(configuration.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @Named("cached")
    public OkHttpClient providesCachedOkHttpClient(Context context) {
        return new OkHttpClient.Builder()
                .cache(new Cache(context.getCacheDir(), 10 * 1024 * 1024))
                .build();
    }

    @Provides
    @Named("cached")
    @Singleton
    public Retrofit providesCachedRetrofit(Configuration configuration, @Named("cached") OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(configuration.getBaseUrl())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public ContactsNetworkService providesContactNetworkService(Retrofit retrofit) {
        return retrofit.create(ContactsNetworkService.class);
    }

    @Provides
    @Named("cached")
    @Singleton
    public ContactsNetworkService providesCachedContactNetworkService(@Named("cached") Retrofit retrofit) {
        return retrofit.create(ContactsNetworkService.class);
    }

    @Provides
    @Singleton
    public ContactsService providesContactService(ContactsNetworkService contactsNetworkService) {
        return new ContactsService(contactsNetworkService);
    }
}
