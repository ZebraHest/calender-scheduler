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

import java.util.LinkedHashMap;
import java.util.List;

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

    @Test
    void testUpdate() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonAdd = new JSONObject();
        jsonAdd.put("title", "1");
        jsonAdd.put("startTime", "2024-05-12T10:00:00");
        jsonAdd.put("endTime", "2024-05-12T11:00:00");
        jsonAdd.put("userId", "1");

        HttpEntity<String> addRequest =
                new HttpEntity<String>(jsonAdd.toString(), headers);

        Object o = restTemplate.postForObject(
                "http://localhost:" + port + "/add",
                addRequest,
                Object.class);

        Object getResult = restTemplate.getForObject("http://localhost:" + port + "/all", Object.class);
        System.out.println(getResult);

        JSONObject jsonUpdate = new JSONObject();
        jsonUpdate.put("title", "2");
        jsonUpdate.put("startTime", "2024-05-12T10:00:00");
        jsonUpdate.put("endTime", "2024-05-12T11:00:00");
        jsonUpdate.put("userId", "1");
        jsonUpdate.put("id", "1");

        HttpEntity<String> request2 =
                new HttpEntity<String>(jsonUpdate.toString(), headers);

        restTemplate.put(
                "http://localhost:" + port + "/update",
                request2);

        Object getResult2 = restTemplate.getForObject("http://localhost:" + port + "/all", Object.class);

        List list = (List) getResult2;
        assert list.size() == 1;
        LinkedHashMap map = (LinkedHashMap) list.getFirst();
        assert map.get("title").equals("2");
    }
}
