package net.chiragaggarwal.contactsapp;

import retrofit2.adapter.rxjava.HttpException;

public interface AddContactView {
    void showFirstNameNotValidDialog();

    void showLastNameNotValidDialog();

    void showPhoneNotValidDialog();

    void onCreateContactSuccess();

    void onCreateContactError(HttpException e);

    void showEmailNotValidDialog();
}
