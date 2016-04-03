package com.deveddy.clujbike.data.api;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.core.StringContains.containsString;

public class ApiSeviceTest {

    private static final String FILE_ENCODING = "UTF-8";
    private static final int OK_CODE = 200;

    private MockWebServer server;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.server = new MockWebServer();
        this.server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    protected void enqueueMockResponse() throws IOException {
        enqueueMockResponse(OK_CODE);
    }

    protected void enqueueMockResponse(int code) throws IOException {
        enqueueMockResponse(code, "{}");
    }

    protected void enqueueMockResponse(int code, String response) throws IOException {
        MockResponse mockResponse = new MockResponse();
        mockResponse.setResponseCode(code);
        mockResponse.setBody(response);
        server.enqueue(mockResponse);
    }

    protected void enqueueMockResponse(String fileName) throws IOException {
        MockResponse mockResponse = new MockResponse();
        String fileContent = getContentFromFile(fileName);
        mockResponse.setBody(fileContent);
        server.enqueue(mockResponse);
    }

    protected void assertRequestSentTo(String url) throws InterruptedException {
        RecordedRequest request = server.takeRequest();
        assertEquals(url, request.getPath());
    }

    protected void assertRequestSentToContains(String... paths) throws InterruptedException {
        RecordedRequest request = server.takeRequest();

        for (String path : paths) {
            Assert.assertThat(request.getPath(), containsString(path));
        }
    }

    protected String getBaseEndpoint() {
        return server.url("/").toString();
    }

    private String getContentFromFile(String fileName) throws IOException {
        fileName = getClass().getResource("/" + fileName).getFile();
        File file = new File(fileName);
        List<String> lines = FileUtils.readLines(file, FILE_ENCODING);
        StringBuilder stringBuilder = new StringBuilder();
        for (String line : lines) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }
}
