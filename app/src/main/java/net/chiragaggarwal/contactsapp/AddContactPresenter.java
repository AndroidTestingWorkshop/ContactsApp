package net.chiragaggarwal.contactsapp;

import retrofit2.adapter.rxjava.HttpException;

public class AddContactPresenter {
    private AddContactView view;
    private ContactsService contactsService;

    public AddContactPresenter(AddContactView view, ContactsService contactsService) {
        this.view = view;
        this.contactsService = contactsService;
    }

    public void addContact(String firstName, String lastName, String phone, String email) {
        if (isNameNotValid(firstName)) {
            view.showFirstNameNotValidDialog();
            return;
        }

        if (isNameNotValid(lastName)) {
            view.showLastNameNotValidDialog();
            return;
        }

        if (isPhoneNotValid(phone)) {
            view.showPhoneNotValidDialog();
            return;
        }

        if (isEmailNotValid(email)) {
            view.showEmailNotValidDialog();
            return;
        }

        Contact contact = new Contact(firstName, lastName, email, phone);
        contactsService.createContact(contact, new ContactsService.CreateContactCallback() {
            @Override
            public void onSuccess(Contact contact) {
                view.onCreateContactSuccess();
            }

            @Override
            public void onFailure(HttpException e) {
                view.onCreateContactError(e);
            }
        });
    }

    private boolean isEmailNotValid(String inputEmail) {
        return !inputEmail.contains("@");
    }

    private boolean isPhoneNotValid(String inputPhone) {
        return inputPhone.isEmpty();
    }

    private boolean isNameNotValid(String name) {
        return name.length() < 2;
    }

}
