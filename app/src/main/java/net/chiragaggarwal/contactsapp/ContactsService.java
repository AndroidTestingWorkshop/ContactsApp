package net.chiragaggarwal.contactsapp;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ContactsService {
    private ContactsNetworkService contactsNetworkService;

    public ContactsService(ContactsNetworkService contactsNetworkService) {
        this.contactsNetworkService = contactsNetworkService;
    }

    public void createContact(Contact contact, final CreateContactCallback callback) {
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
                        callback.onFailure(((HttpException) e));
                    }

                    @Override
                    public void onNext(Contact contact) {
                        callback.onSuccess(contact);
                    }
                });
    }

    public interface CreateContactCallback {
        void onSuccess(Contact contact);

        void onFailure(HttpException e);
    }
}
