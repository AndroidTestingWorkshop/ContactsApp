package net.chiragaggarwal.contactsapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Retrofit;
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
        addContactDetailTextChangedListeners(buttonAddContact, inputFirstName, inputEmail, inputPhone, inputLastName);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        final ContactsNetworkService contactsNetworkService = retrofit.create(ContactsNetworkService.class);
        buttonAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact contact = new Contact(contentof(inputFirstName), contentof(inputLastName), contentof(inputEmail), contentof(inputPhone));
                contactsNetworkService.createContact(contact)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Contact>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                            }

                            @Override
                            public void onNext(Contact contact) {
                                new AlertDialog.Builder(AddContactActivity.this)
                                        .setMessage(R.string.contact_created)
                                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        }).create().show();
                            }
                        });
            }
        });
    }

    private void addContactDetailTextChangedListeners(final TextView... textViews) {
        for (TextView textView : textViews) {
            textView.addTextChangedListener(new ContactDetailTextChangeWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    toggleAddContactButtonState(buttonAddContact, inputFirstName, inputLastName, inputEmail, inputPhone);
                }
            });
        }
    }

    private void toggleAddContactButtonState(TextView buttonAddContact, EditText inputFirstName, EditText inputLastName, EditText inputEmail, EditText inputPhone) {
        boolean areRequiredFieldsPresent = isPresent(inputFirstName) && isPresent(inputLastName) && isPresent(inputEmail) && isPresent(inputPhone);
        buttonAddContact.setBackgroundColor(areRequiredFieldsPresent ? getResources().getColor(R.color.add_contact_green) : getResources().getColor(R.color.light_gray));
        buttonAddContact.setEnabled(areRequiredFieldsPresent);
    }

    private boolean isPresent(EditText input) {
        return !isNotPresent(input);
    }

    private boolean isNotPresent(EditText input) {
        return input.getText().toString().length() < 2;
    }

    private String contentof(TextView textView) {
        return textView.getText().toString();
    }
}
