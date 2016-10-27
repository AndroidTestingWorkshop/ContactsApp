package net.chiragaggarwal.contactsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ContactActivity extends AppCompatActivity {
    private static final int DEFAULT_CONTACT_ID = 1;
    private TextView textName;
    private TextView textPhone;
    private TextView textEmail;

    @Inject
    public ContactsNetworkService contactsNetworkService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ((ContactsApplication) getApplication()).getContactsComponent().inject(this);

        textName = (TextView) findViewById(R.id.text_name);
        textPhone = (TextView) findViewById(R.id.text_phone);
        textEmail = (TextView) findViewById(R.id.text_email);
        final int contactId = getIntent().getIntExtra(Contact.ID, DEFAULT_CONTACT_ID);

        contactsNetworkService
                .getContact(contactId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Contact>() {
                    @Override
                    public void onCompleted() {
                        System.out.println();
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println();
                    }

                    @Override
                    public void onNext(Contact contact) {
                        textName.setText(contact.firstName + " " + contact.lastName);
                        textEmail.setText(contact.email);
                        textPhone.setText(contact.phoneNumber);
                    }
                });
    }
}
