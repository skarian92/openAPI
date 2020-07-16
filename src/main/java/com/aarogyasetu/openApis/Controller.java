package com.aarogyasetu.openApis;

import com.aarogyasetu.pojo.*;
import com.aarogyasetu.sms.SMSManager;
import com.aarogyasetu.uipojo.StatusRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping(value = "/openapis")
public class Controller {

    @Autowired
    private OpenApiServices openApiServices;

    @Autowired
    private SMSManager smsManager;

    Map<String, CallbackRequest> map = new HashMap<>();
    private String OTP = "";

    /**
     * Health Check URL
     * @return
     */
    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public String health() {
        return "HEALTHY";
    }

    /**
     * Method to render UI
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showUI() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("openApi.html");
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream, Charset.defaultCharset()))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        return stringBuilder.toString();
    }

    /**
     * API to call /userstatus api which returns request id immediately and calls callback url
     * @param statusRequest
     * @return request id
     * @throws IOException
     */
    @RequestMapping(value = "/status", method = RequestMethod.POST, consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public String userStatus(@org.springframework.web.bind.annotation.RequestBody StatusRequest statusRequest) throws IOException {

        //GET Token
        String token = openApiServices.getToken();
        UserStatusResponse userStatusRequestId = openApiServices.getUserStatusRequestId(token, statusRequest.getPhoneNumber());
        return userStatusRequestId.getRequestId();

    }

    /**
     * API which keeps on polling the response from callback url and displays on UI
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/showCallbackRespInUI", method = RequestMethod.GET)
    public CallbackRequest getCalbackResponse(@RequestParam String requestId){
        CallbackRequest req = null;
        if (map.containsKey(requestId)){
            req = map.get(requestId);
            map.remove(requestId);
        }
        return req;
    }

    /**
     * Creating a sample callback url api to mention in My Profile section where  /userstatus api gives back status of requested user
     * @param callbackRequest
     */
    @RequestMapping(value = "/sampleCallback", method = RequestMethod.POST)
    public void callbackurl(@org.springframework.web.bind.annotation.RequestBody CallbackRequest callbackRequest){
        map.put(callbackRequest.getRequest_id(), callbackRequest);
    }

    @RequestMapping(value = "/sendOTP", method = RequestMethod.GET)
    public void sendOTP(@RequestParam String phoneNumber){
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        OTP = String.format("%06d", number);
        smsManager.sendSMSMessage(OTP, phoneNumber);
    }


    @RequestMapping(value = "/validateOTP", method = RequestMethod.GET)
    public boolean validateOTP(String otp){
        return otp.equals(OTP);
    }



}
