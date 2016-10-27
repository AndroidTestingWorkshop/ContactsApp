package net.chiragaggarwal.contactsapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import net.chiragaggarwal.contactsapp.dependencies.AppModule;
import net.chiragaggarwal.contactsapp.dependencies.ConfigurationModule;
import net.chiragaggarwal.contactsapp.dependencies.ContactsComponent;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class ContactsTestEnvironment implements TestRule {
    private static final String BASE_URL = "http://localhost:4567/";

    @Override
    public Statement apply(Statement base, Description description) {
        return new OpenWeatherMapTestEnvironmentStatement(base);
    }

    private class OpenWeatherMapTestEnvironmentStatement extends Statement {
        private Statement statement;

        public OpenWeatherMapTestEnvironmentStatement(Statement statement) {
            this.statement = statement;
        }

        @Override
        public void evaluate() throws Throwable {
            Context context = InstrumentationRegistry.getTargetContext();
            ContactsComponent contactsComponent = net.chiragaggarwal.contactsapp.dependencies.DaggerContactsComponent.builder()
                    .appModule(new AppModule(context))
                    .configurationModule(new ConfigurationModule(BASE_URL))
                    .build();
            ((ContactsApplication) context.getApplicationContext()).setContactsComponent(contactsComponent);
            statement.evaluate();
        }
    }
}
