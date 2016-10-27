package net.chiragaggarwal.contactsapp.dependencies;

import net.chiragaggarwal.contactsapp.AddContactActivity;
import net.chiragaggarwal.contactsapp.ContactActivity;
import net.chiragaggarwal.contactsapp.ContactsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ConfigurationModule.class, NetworkModule.class})
public interface ContactsComponent {
    void inject(AddContactActivity addContactActivity);

    void inject(ContactsActivity contactsActivity);

    void inject(ContactActivity contactActivity);
}
