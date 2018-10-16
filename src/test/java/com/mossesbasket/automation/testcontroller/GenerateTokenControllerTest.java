package com.mossesbasket.automation.testcontroller;

import com.mossesbasket.automation.Constant;
import com.mossesbasket.automation.Util;
import com.mossesbasket.automation.model.tokengenerate.TokenGenerateBaseModel;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.AfterTest;
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
import static org.testng.Assert.assertNotNull;

public class GenerateTokenControllerTest {

    private CloseableHttpClient client;
    private CloseableHttpResponse httpResponse;
    private JSONObject validateJSON;
    private TokenGenerateBaseModel modelResponse;

    @BeforeTest
    public void setUpAndDoRequest() throws URISyntaxException, IOException {

        String GENERATE_TOKEN = "/auth/security/generateToken";

        // Client
        client = HttpClients.createDefault();
        HttpPost post = new HttpPost(Constant.BASE_URL + GENERATE_TOKEN);

        // Add headers
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-Type", "application/json");

        ClassLoader loader = ClassLoader.getSystemClassLoader();
        String reqJsonString = Files.lines(Paths.get(loader.getResource("request_payloads/token_generate_req.json").toURI()))
                .parallel()
                .collect(Collectors.joining());
        StringEntity entity = new StringEntity(reqJsonString);
        post.setEntity(entity);

        // Execute the request
        httpResponse = client.execute(post);
        modelResponse = Util.retrieveResourceFromResponse(
                httpResponse, TokenGenerateBaseModel.class);

        // Given
        String jsonString = Files.lines(Paths.get(loader.getResource("response_payloads/token_generate_success.json").toURI()))
                .parallel()
                .collect(Collectors.joining());
        validateJSON = new JSONObject(jsonString);
    }

    @Test
    public void getTokenGenerateStatusCode() {
        // Then
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void validateTokenGenerateMimeType() {
        // Given
        String jsonMimeType = "application/json";

        // Then
        String mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();
        assertEquals( jsonMimeType, mimeType );
    }

    @Test
    public void validatePayloadMessage() {
        assertThat(validateJSON.get("msg"), Matchers.is(modelResponse.getMsg()));
    }

    @Test
    public void validateTokenObjectExistence() {
        assert (modelResponse.getRes().size() > 0);
    }

    @Test
    public void validateTokenGenerated() {
        assertNotNull(modelResponse.getRes().get(0).getData().getAccessToken());
    }

    @Test
    public void validateGeneratedTokenValid() {
        assertNotNull(modelResponse.getRes().get(0).getData().getAccessToken());
        assertThat("valid", Matchers.is(modelResponse.getRes().get(0).getData().getStatus()));
    }

    @AfterTest
    public void closeRequest() throws IOException {
        client.close();
    }
}
