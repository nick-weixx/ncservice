package com.nick.weixx.cloud.az.provider.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AzkabanClient {


    public AzkabanClient() {

    }

    RestTemplate restTemplate;
    String HOST;
    String USER_NAME = "azkaban";
    String PASS_WORD = "azkaban";
    volatile String SESSION_ID;

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

    /**
     * 每小时自动更新一次sessionid
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public synchronized void login() {
        LinkedMultiValueMap<String, Object> requestParam = new LinkedMultiValueMap<String, Object>();
        requestParam.add("action", "login");
        requestParam.add("username", USER_NAME);
        requestParam.add("password", PASS_WORD);
        String body = "";
        try {
            HttpHead httpHead = new HttpHead() {
                @Override
                public HttpHeaders builer() {
                    return new HttpHeaders();
                }
            };
            body = this.sendPost(httpHead, "", requestParam);
        } catch (AzkabanClientException azException) {
            log.error(azException.getMessage());
//            System.out.println(azException.getMessage());
        } finally {

        }


        JsonObject obj = new JsonParser().parse(body).getAsJsonObject();
        String sessionId = "";
        if ("success".equals(obj.get("status").getAsString())) {
            sessionId = obj.get("session.id").getAsString();
            log.info("azkaban servier session id is {}", sessionId);
        } else {
            //TODO add log
            log.error("login azkaban servier error..{}", obj.get("message").toString());
        }
        SESSION_ID = sessionId;
//        return sessionId;
    }

    public String sendPost(final HttpHead httpHead, final LinkedMultiValueMap<String, Object> requestParam) throws AzkabanClientException {
        return sendPost(httpHead, "", requestParam);
//        params.add("session.id",SESSION_ID);
    }


    /**
     * get 请求
     *
     * @param action
     * @param params
     * @return
     * @throws AzkabanClientException
     */
    public String sendGet(final HttpHead head, final String action, final String params) throws AzkabanClientException {

        String body = "";
        try {
            StringBuilder strBuiler = new StringBuilder();
            strBuiler.append(HOST).append(action).append("?session.id=").append(SESSION_ID).append(params);


            HttpHeaders headers = head.builer();
            if (!headers.containsKey("Content-Type"))
                headers.add("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            else if (!headers.containsKey("X-Requested-With"))
                headers.add("X-Requested-With", "XMLHttpRequest");
            else if (!headers.containsKey("Accept"))
                headers.add("Accept", "text/plain;charset=utf-8");


//            ResponseEntity<String> rs = restTemplate.exchange(
//                    strBuiler.toString(), HttpMethod.GET,
//                    new HttpEntity<String>(headers), String.class, params);


            ResponseEntity<String> rs = restTemplate.exchange(strBuiler.toString(), HttpMethod.GET, new HttpEntity<>(headers), String.class);
            if (HttpStatus.OK == rs.getStatusCode()) {
                return "{ \"status\":\"success\",\"action\":\"" + action + "\"}";
            } else {
                log.error("azkaban request error: {}", rs.getStatusCode().value());
                AzkabanClientException azException = new AzkabanClientException("azkaban 请求失败...");
                throw azException;
            }

//            ResponseEntity<String> rs = restTemplate.getForEntity(strBuiler.toString(), String.class);
//            body = rs.getBody();
        } catch (ResourceAccessException ex) {
//            ex.printStackTrace();
//            log.error(ex.getMessage());
            AzkabanClientException azException = new AzkabanClientException("azkaban 服务连接异常...");
            throw azException;
        } catch (Exception e) {
//            log.error(e.getMessage());
            AzkabanClientException azException = new AzkabanClientException("azkaban 服务连接异常...");
        } finally {

        }

        return body;
    }

    /**
     * post 请求发送
     *
     * @param action
     * @param requestParam
     * @return
     * @throws AzkabanClientException
     */
    public String sendPost(final HttpHead head, final String action, final LinkedMultiValueMap<String, Object> requestParam) throws AzkabanClientException {
        String body = "";
        //TODO 控制传入的session,后期根据action的值，控制是否需要添加session.id
        ///manager?action=create
        if (StringUtils.isNotBlank(action)) {
            requestParam.add("session.id", SESSION_ID);
        }

        HttpHeaders headers = head.builer();
        if (!headers.containsKey("Content-Type"))
            headers.add("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        else if (!headers.containsKey("X-Requested-With"))
            headers.add("X-Requested-With", "XMLHttpRequest");
        else if (!headers.containsKey("Accept"))
            headers.add("Accept", "text/plain;charset=utf-8");


        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(requestParam, headers);
        try {
            ResponseEntity<String> rs = restTemplate.postForEntity(HOST + action, httpEntity, String.class);
            body = rs.getBody();
        } catch (ResourceAccessException ex) {
            //TODO add logs
//            ex.printStackTrace();
//            log.error(ex.getMessage());
            AzkabanClientException azException = new AzkabanClientException("azkaban 服务连接异常...");
            throw azException;
        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
            AzkabanClientException azException = new AzkabanClientException("azkaban 服务连接异常...");
        } finally {

        }
        return body;
    }


    public interface HttpHead {
        public HttpHeaders builer();
    }
}

