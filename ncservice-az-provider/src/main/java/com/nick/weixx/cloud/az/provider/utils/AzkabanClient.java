package com.nick.weixx.cloud.az.provider.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

public class AzkabanClient {


    public AzkabanClient() {

    }

    RestTemplate restTemplate;
    String HOST;
    String USER_NAME = "azkaban";
    String PASS_WORD = "azkaban";
    String SESSION_ID;

    public AzkabanClient(RestTemplate restTemplate, String host) {
        this.restTemplate = restTemplate;
        this.HOST = host;
        login();
    }


    public AzkabanClient(RestTemplate restTemplate, String host, String username, String password) {
        this.restTemplate = restTemplate;
        this.HOST = host;
        this.USER_NAME = username;
        this.PASS_WORD = password;
        login();
    }

    public void login() {
        LinkedMultiValueMap<String, String> requestParam = new LinkedMultiValueMap<String, String>();
        requestParam.add("action", "login");
        requestParam.add("username", USER_NAME);
        requestParam.add("password", PASS_WORD);
        String body = "";
        try {
            body = this.sendPost("", requestParam);
        } catch (AzkabanClientException azException) {
            System.out.println(azException.getMessage());
        } finally {

        }


        JsonObject obj = new JsonParser().parse(body).getAsJsonObject();
        String sessionId = "";
        if ("success".equals(obj.get("status").getAsString())) {
            sessionId = obj.get("session.id").getAsString();
        } else {
            //TODO add log
            System.out.print(obj.get("message").toString());
        }
        SESSION_ID = sessionId;
//        return sessionId;
    }

    public String sendPost(final LinkedMultiValueMap<String, String> requestParam) throws AzkabanClientException {
        return sendPost("", requestParam);
//        params.add("session.id",SESSION_ID);
    }

    public String sendPost(final String action, final LinkedMultiValueMap<String, String> requestParam) throws AzkabanClientException {
        String body = "";
        //TODO 控制传入的session,后期根据action的值，控制是否需要添加session.id
        ///manager?action=create
        if (StringUtils.isNotBlank(action)) {
            requestParam.add("session.id", SESSION_ID);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        headers.add("X-Requested-With", "XMLHttpRequest");

        HttpEntity<LinkedMultiValueMap<String, String>> httpEntity = new HttpEntity<>(requestParam, headers);
        try {
            ResponseEntity<String> rs = restTemplate.postForEntity(HOST + action, httpEntity, String.class);
            body = rs.getBody();
        } catch (ResourceAccessException ex) {
            //TODO add logs
            ex.printStackTrace();
            AzkabanClientException azException = new AzkabanClientException("azkaban 服务连接异常...");
            throw azException;
        } catch (Exception e) {
            e.printStackTrace();
            AzkabanClientException azException = new AzkabanClientException("azkaban 服务连接异常...");
        } finally {

        }


        return body;
    }
}

