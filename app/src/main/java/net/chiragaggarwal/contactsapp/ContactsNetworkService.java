package net.chiragaggarwal.contactsapp;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import rx.Observable;

public interface ContactsNetworkService {
    @GET("contacts")
    @Headers("Accept: application/json")
    Observable<List<Contact>> getContacts();
}
