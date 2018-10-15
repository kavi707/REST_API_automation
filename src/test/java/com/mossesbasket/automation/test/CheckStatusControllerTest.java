package com.mossesbasket.automation.test;

import com.mossesbasket.automation.Constant;
import com.mossesbasket.automation.Util;
import com.mossesbasket.automation.model.checkstatus.CheckStatusBaseModel;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.testng.Assert.assertEquals;

public class CheckStatusControllerTest {

    private String CHECK_STATUS = "/auth/status";
    private HttpResponse httpResponse;

    @BeforeTest
    public void setUpAndDoRequest() throws IOException {
        HttpUriRequest request = new HttpGet(Constant.BASE_URL + CHECK_STATUS);

        // When
        httpResponse = HttpClientBuilder.create().build().execute( request );
    }

    @Test
    public void validateCheckStatusStatusCode() {
        // Then
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void validateCheckStatusMimeType() {
        // Given
        String jsonMimeType = "application/json";

        // Then
        String mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();
        assertEquals( jsonMimeType, mimeType );
    }

    @Test
    public void validateCheckStatusPayload() throws URISyntaxException, IOException {
        // Given
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        String jsonString = Files.lines(Paths.get(loader.getResource("payloads/check_status_success.json").toURI()))
                .parallel()
                .collect(Collectors.joining());
        JSONObject validateJSON = new JSONObject(jsonString);

        CheckStatusBaseModel resource = Util.retrieveResourceFromResponse(
                httpResponse, CheckStatusBaseModel.class);

        assertThat( validateJSON.get("msg"), Matchers.is( resource.getMsg() ) );
    }
}
