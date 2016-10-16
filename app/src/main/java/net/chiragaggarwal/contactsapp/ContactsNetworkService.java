package net.chiragaggarwal.contactsapp;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface ContactsNetworkService {
    @GET("contacts")
    @Headers("Accept: application/json")
    Observable<List<Contact>> getContacts();

    @GET("contacts/{contactId}")
    @Headers("Accept: application/json")
    Observable<Contact> getContact(@Path("contactId") int contactId);

    @POST("contacts.json")
    Observable<Contact> createContact(@Body Contact contact);
}
