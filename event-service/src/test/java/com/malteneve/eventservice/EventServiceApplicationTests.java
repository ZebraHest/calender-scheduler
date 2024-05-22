package com.malteneve.eventservice;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "eureka.client.enabled=false")
class EventServiceApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void contextLoads() {
    }

    @Test
    void testReceive() {
        //Create Accounts
        Object data1 = this.restTemplate.getForObject(
                "http://localhost:" + port + "/all",
                Object.class);

        System.out.println(data1);
    }

    @Test
    void addTestValidation() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", "1");
        jsonObject.put("isRepeating", "true");

        HttpEntity<String> request =
                new HttpEntity<String>(jsonObject.toString(), headers);

        Object o = restTemplate.postForObject(
                "http://localhost:" + port + "/add",
                request,
                Object.class);

        System.out.println(o);
    }

}
