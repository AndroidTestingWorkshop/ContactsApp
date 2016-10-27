package net.chiragaggarwal.contactsapp.dependencies;

import android.content.Context;

import net.chiragaggarwal.contactsapp.ContactsNetworkService;
import net.chiragaggarwal.contactsapp.ContactsService;
import net.chiragaggarwal.contactsapp.R;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    @Provides
    @Singleton
    public Retrofit providesRetrofit(Context applicationContext) {
        return new Retrofit.Builder()
                .baseUrl(applicationContext.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    public ContactsNetworkService providesContactNetworkService(Retrofit retrofit) {
        return retrofit.create(ContactsNetworkService.class);
    }

    @Provides
    @Singleton
    public ContactsService providesContactService(ContactsNetworkService contactsNetworkService) {
        return new ContactsService(contactsNetworkService);
    }
}
