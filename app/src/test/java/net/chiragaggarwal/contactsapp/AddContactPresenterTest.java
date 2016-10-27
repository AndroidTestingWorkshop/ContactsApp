package net.chiragaggarwal.contactsapp;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.net.UnknownHostException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

import static net.chiragaggarwal.contactsapp.ContactsService.CreateContactCallback;

public class AddContactPresenterTest {
    @Test
    public void testThatItKnowsThatTheCreationOfContactHasBeenSuccessfulWhenValidationPassesAndServerProvidesAptResponse() {
        AddContactView view = Mockito.mock(AddContactView.class);
        ContactsService service = Mockito.mock(ContactsService.class);
        final Contact contact = new Contact("Chirag", "Aggarwal", "9999999999", "chi6rag@gmail.com");

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                CreateContactCallback createContactCallback = (CreateContactCallback) invocation.getArguments()[0];
                createContactCallback.onSuccess(contact);
                return null;
            }
        }).when(service).createContact(Matchers.<Contact>any(), Matchers.<CreateContactCallback>any());
        AddContactPresenter addContactPresenter = new AddContactPresenter(view, service);
        addContactPresenter.addContact("Chirag", "Aggarwal", "9999999999", "chi6rag@gmail.com");
        Mockito.verify(view).onCreateContactSuccess();
    }

    @Test
    public void testThatNoInternetConnectionErrorIsShownWhenNotConnectedToInternet() {
        AddContactView view = Mockito.mock(AddContactView.class);
        ContactsService service = Mockito.mock(ContactsService.class);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                CreateContactCallback createContactCallback = (CreateContactCallback) invocation.getArguments()[1];
                createContactCallback.onFailure(new UnknownHostException("host not found"));
                return null;
            }
        }).when(service).createContact(Matchers.<Contact>any(), Matchers.<CreateContactCallback>any());
        AddContactPresenter addContactPresenter = new AddContactPresenter(view, service);
        addContactPresenter.addContact("Chirag", "Aggarwal", "9999999999", "chi6rag@gmail.com");
        Mockito.verify(view).showNoInternetConnectionError();
    }

    @Test
    public void testBla() throws IOException {
        AddContactView view = Mockito.mock(AddContactView.class);
        ContactsService service = Mockito.mock(ContactsService.class);
        String content = "{\"message\": \"You are not authorized\", \"code\": 401\"}";
        final HttpException exception = getUnauthorizedError(content);

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                CreateContactCallback createContactCallback = (CreateContactCallback) invocation.getArguments()[1];
                createContactCallback.onFailure(exception);
                return null;
            }
        }).when(service).createContact(Matchers.<Contact>any(), Matchers.<CreateContactCallback>any());
        AddContactPresenter addContactPresenter = new AddContactPresenter(view, service);
        addContactPresenter.addContact("Chirag", "Aggarwal", "9999999999", "chi6rag@gmail.com");
        Mockito.verify(view).onCreateContactError(content);
    }

    private HttpException getUnauthorizedError(String content) {
        ResponseBody responseBody = ResponseBody.create(MediaType.parse("UTF-8"), content);
        Response response = Response.error(401, responseBody);
        return new HttpException(response);
    }
}