package net.chiragaggarwal.contactsapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddContactActivity extends AppCompatActivity {
    private TextView buttonAddContact;
    private EditText inputFirstName;
    private EditText inputLastName;
    private EditText inputEmail;
    private EditText inputPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        buttonAddContact = (TextView) findViewById(R.id.button_add_contact);
        inputFirstName = (EditText) findViewById(R.id.input_first_name);
        inputLastName = (EditText) findViewById(R.id.input_last_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPhone = (EditText) findViewById(R.id.input_phone);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        final ContactsNetworkService contactsNetworkService = retrofit.create(ContactsNetworkService.class);

        buttonAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contentof(inputFirstName).length() < 2) {
                    new AlertDialog.Builder(AddContactActivity.this)
                            .setMessage(R.string.error_invalid_name)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).create().show();
                    return;
                }

                if (contentof(inputLastName).length() < 2) {
                    new AlertDialog.Builder(AddContactActivity.this)
                            .setMessage(R.string.error_invalid_name)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).create().show();
                    return;
                }

                if (inputPhone.getText().toString().isEmpty()) {
                    new AlertDialog.Builder(AddContactActivity.this)
                            .setMessage(R.string.error_invalid_phone)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).create().show();
                    return;
                }

                if (!contentof(inputEmail).contains("@")) {
                    new AlertDialog.Builder(AddContactActivity.this)
                            .setMessage(R.string.error_invalid_email)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).create().show();
                    return;
                }

                Contact contact = new Contact(contentof(inputFirstName), contentof(inputLastName), contentof(inputEmail), contentof(inputPhone));
                contactsNetworkService.createContact(contact)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Contact>() {
                            @Override
                            public void onCompleted() {
                                System.out.println();
                            }

                            @Override
                            public void onError(Throwable e) {
                                try {
                                    new AlertDialog.Builder(AddContactActivity.this)
                                            .setMessage(((HttpException) e).response().errorBody().string())
                                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            }).create().show();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }

                            @Override
                            public void onNext(Contact contact) {
                                finish();
                            }
                        });
            }
        });
    }

    private String contentof(TextView textView) {
        return textView.getText().toString();
    }
}
