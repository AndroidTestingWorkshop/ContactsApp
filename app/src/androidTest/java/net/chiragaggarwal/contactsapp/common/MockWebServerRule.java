package net.chiragaggarwal.contactsapp.common;

import org.hamcrest.Matcher;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class MockWebServerRule implements TestRule {
    private static final int PORT = 4567;
    private MockHTTPDispatcher dispatcher;
    private MockWebServer mockWebServer;

    public MockWebServerRule() {
        mockWebServer = new MockWebServer();
        dispatcher = new MockHTTPDispatcher();
        mockWebServer.setDispatcher(dispatcher);
    }

    @Override
    public Statement apply(Statement statement, Description description) {
        return new MockHTTPServerStatement(statement);
    }

    public void mock(Matcher<RecordedRequest> requestMatcher, int httpResponseCode, String response, Headers headers) throws IOException {
        MockResponse mockResponse = new MockResponse()
                .setResponseCode(httpResponseCode)
                .setBody(response);
        if (headers != null)
            mockResponse.setHeaders(headers);
        dispatcher.mock(requestMatcher, mockResponse);
    }

    private class MockHTTPServerStatement extends Statement {

        private Statement base;

        public MockHTTPServerStatement(Statement base) {
            this.base = base;
        }

        @Override
        public void evaluate() throws Throwable {
            mockWebServer.start(PORT);
            try {
                this.base.evaluate();
            } finally {
                mockWebServer.shutdown();
            }
        }
    }
}
