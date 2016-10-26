package net.chiragaggarwal.contactsapp;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

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
}