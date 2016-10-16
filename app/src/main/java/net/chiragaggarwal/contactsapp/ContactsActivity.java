package net.chiragaggarwal.contactsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ContactsActivity extends AppCompatActivity {
    private RecyclerView listContacts;
    private ContactsAdapter contactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        listContacts = (RecyclerView) findViewById(R.id.list_contacts);
        listContacts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        contactsAdapter = new ContactsAdapter(this);
        listContacts.setAdapter(contactsAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ContactsNetworkService contactsNetworkService = retrofit.create(ContactsNetworkService.class);
        contactsNetworkService.getContacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Contact>>() {
                    @Override
                    public void onCompleted() {
                        System.out.println();
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println();
                    }

                    @Override
                    public void onNext(List<Contact> contacts) {
                        contactsAdapter.populate(contacts);
                    }
                });
    }
}
