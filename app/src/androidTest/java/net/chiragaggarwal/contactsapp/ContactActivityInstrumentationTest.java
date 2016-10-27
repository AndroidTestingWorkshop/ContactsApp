package net.chiragaggarwal.contactsapp;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import net.chiragaggarwal.contactsapp.common.AssetUtils;
import net.chiragaggarwal.contactsapp.common.MockWebServerRule;
import net.chiragaggarwal.contactsapp.common.RequestMatchers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class ContactActivityInstrumentationTest {
    @Rule
    public ContactsTestEnvironment contactsTestEnvironment = new ContactsTestEnvironment();

    @Rule
    public MockWebServerRule mockWebServerRule = new MockWebServerRule();

    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule(ContactActivity.class, true, false);

    @Test
    public void testBla() throws Exception {
        Intent intent = new Intent();
        intent.putExtra(Contact.ID, 76);
        String responseBody = AssetUtils.readAsset("contact.json");
        mockWebServerRule.mock(allOf(RequestMatchers.httpMethodIs("GET"), RequestMatchers.pathStartsWith("/contacts/76")), 200, responseBody, null);
        activityTestRule.launchActivity(intent);
        onView(withId(R.id.text_name)).check(matches(withText("gandalf1 user")));
    }
}
