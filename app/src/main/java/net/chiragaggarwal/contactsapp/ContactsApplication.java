package net.chiragaggarwal.contactsapp;

import android.app.Application;

import net.chiragaggarwal.contactsapp.dependencies.AppModule;
import net.chiragaggarwal.contactsapp.dependencies.ContactsComponent;
import net.chiragaggarwal.contactsapp.dependencies.DaggerContactsComponent;

public class ContactsApplication extends Application {
    private ContactsComponent contactsComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        contactsComponent = DaggerContactsComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public ContactsComponent getContactsComponent() {
        return contactsComponent;
    }

    public void setContactsComponent(ContactsComponent contactsComponent) {
        this.contactsComponent = contactsComponent;
    }
}
