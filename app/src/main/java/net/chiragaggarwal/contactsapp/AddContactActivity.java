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

public class AddContactActivity extends AppCompatActivity implements AddContactView {
    private TextView buttonAddContact;
    private EditText inputFirstName;
    private EditText inputLastName;
    private EditText inputEmail;
    private EditText inputPhone;
    private AddContactPresenter addContactPresenter;

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
        addContactPresenter = new AddContactPresenter(this, new ContactsService(contactsNetworkService));

        buttonAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContactPresenter.addContact(inputFirstName.getText().toString(), inputLastName.getText().toString(), inputPhone.getText().toString(), inputEmail.getText().toString());
            }
        });
    }

    @Override
    public void onCreateContactSuccess() {
        finish();
    }

    @Override
    public void onCreateContactError(HttpException e) {
        try {
            new AlertDialog.Builder(AddContactActivity.this)
                    .setMessage(e.response().errorBody().string())
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
    public void showEmailNotValidDialog() {
        new AlertDialog.Builder(AddContactActivity.this)
                .setMessage(R.string.error_invalid_email)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create().show();
    }

    @Override
    public void showPhoneNotValidDialog() {
        new AlertDialog.Builder(AddContactActivity.this)
                .setMessage(R.string.error_invalid_phone)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create().show();
    }

    @Override
    public void showLastNameNotValidDialog() {
        new AlertDialog.Builder(AddContactActivity.this)
                .setMessage(R.string.error_invalid_name)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create().show();
    }

    @Override
    public void showFirstNameNotValidDialog() {
        new AlertDialog.Builder(AddContactActivity.this)
                .setMessage(R.string.error_invalid_name)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create().show();
    }
}
