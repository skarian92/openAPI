package com.aarogyasetu.openApis;

import com.aarogyasetu.pojo.TokenResponse;
import com.aarogyasetu.pojo.UserStatusByReqIdResponse;
import com.aarogyasetu.pojo.UserStatusRequest;
import com.aarogyasetu.pojo.UserStatusResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Contain open APIs
 */
@Component
public class OpenApiServices {

    @Qualifier("getObjectMapper")
    @Autowired
    private ObjectMapper objectMapper;

    String host = "https://api.aarogyasetu.gov.in";

    /**
     * /token api
     * @return
     * @throws IOException
     */
    public String getToken() throws IOException {
        //Properties properties = getProperties();
        String username = System.getProperty("username");
        String password = System.getProperty("password");
        String apiKey = System.getProperty("x-api-key");

        String tokenUrl = host + "/token";

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        String reqBody = String.format("{\n    \"username\": \"%s\",\n    \"password\": \"%s\"\n}",username,password);
        RequestBody body = RequestBody.create(mediaType, reqBody);
        Request request = new Request.Builder()
                .url(tokenUrl)
                .method("POST", body)
                .addHeader("accept", "application/json")
                .addHeader("x-api-key", apiKey)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        String resBody = response.body().string();
        ObjectMapper om = new ObjectMapper();
        TokenResponse tokenResponse = om.readValue(resBody, TokenResponse.class);
        return tokenResponse.getToken();
    }

    /**
     * /userstatus api
     * @param token
     * @param phoneNumber : phone number of ther user whose Aarogya status needs to be  queried
     * @return
     * @throws IOException
     */
    public UserStatusResponse getUserStatusRequestId(String token, String phoneNumber) throws IOException {
        //Properties properties = getProperties();
        String apiKey = System.getProperty("x-api-key");

        String userStatusUrl = host + "/userstatus";
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        UserStatusRequest userStatusRequest = new UserStatusRequest(phoneNumber);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, objectMapper.writeValueAsString(userStatusRequest));
        Request request = new Request.Builder()
                .url(userStatusUrl)
                .method("POST", body)
                .addHeader("accept", "application/json")
                .addHeader("x-api-key", apiKey)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        ObjectMapper om = new ObjectMapper();
        UserStatusResponse userStatusResponse = om.readValue(response.body().string(), UserStatusResponse.class);
        return userStatusResponse;
    }


    /**
     * NOt being used anywhere in this project....Just for reference
     * @param token
     * @param requestId
     * @return
     * @throws IOException
     */
    private UserStatusByReqIdResponse getUserStatusByReqId(String token, String requestId) throws IOException {
        //Properties properties = getProperties();
        String apiKey = System.getProperty("x-api-key");

        String userstatusbyreqidUrl = host + "/userstatusbyreqid";

        String reqBody = String.format("{\n  \"requestId\": %s \n}", requestId);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, reqBody);
        Request request = new Request.Builder()
                .url(userstatusbyreqidUrl)
                .method("POST", body)
                .addHeader("x-api-key", apiKey)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        ObjectMapper om = new ObjectMapper();
        UserStatusByReqIdResponse userStatusByReqIdResponse = om.readValue(response.body().string(), UserStatusByReqIdResponse.class);
        return userStatusByReqIdResponse;
    }


    private Properties getProperties() throws IOException {
        Properties prop=new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");

        prop.load(inputStream);

        return prop;
    }
}
