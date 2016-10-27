package net.chiragaggarwal.contactsapp.common;

import android.util.Log;

import org.hamcrest.Matcher;

import java.util.HashMap;
import java.util.Map;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

public class MockHTTPDispatcher extends Dispatcher {
    public static final String TEST_TAG = "WEATHER_LENS_TEST";

    private Map<Matcher<RecordedRequest>, MockResponse> requestResponseMap;

    public MockHTTPDispatcher() {
        requestResponseMap = new HashMap<>();
    }

    public MockHTTPDispatcher mock(Matcher<RecordedRequest> matcher, MockResponse mockResponse) {
        requestResponseMap.put(matcher, mockResponse);
        return this;
    }

    @Override
    public MockResponse dispatch(RecordedRequest recordedRequest) {
        String recordedRequestPath = recordedRequest.getPath();
        for (Matcher<RecordedRequest> mockRequest : requestResponseMap.keySet()) {
            if (mockRequest.matches(recordedRequest)) {
                MockResponse response = requestResponseMap.get(mockRequest);
                Log.d(TEST_TAG, "Returning response for path " + recordedRequestPath);
                return response;
            }
        }
        Log.e(TEST_TAG, "Not able to find path " + recordedRequestPath);
        return new MockResponse().setResponseCode(404);
    }

    public void clear() {
        requestResponseMap.clear();
    }
}
