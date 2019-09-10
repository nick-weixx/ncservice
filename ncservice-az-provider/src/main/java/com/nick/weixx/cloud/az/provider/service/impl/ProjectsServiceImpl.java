package com.nick.weixx.cloud.az.provider.service.impl;

import com.nick.weixx.cloud.az.provider.service.IProjectsService;
import com.nick.weixx.cloud.az.provider.utils.AzkabanClient;
import com.nick.weixx.cloud.az.provider.utils.AzkabanClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nick weixinxing0226@gmail.com
 * @version create：2019/9/9
 */
@Service
@Slf4j
public class ProjectsServiceImpl implements IProjectsService {

    @Autowired
    AzkabanClient azkabanClient;


    @Override
    public String createProject(String name, String description) {
        LinkedMultiValueMap<String, Object> requestParam = new LinkedMultiValueMap<String, Object>();
        requestParam.add("name", name);
        requestParam.add("description", description);
        String body = "";
        try {
            AzkabanClient.HttpHead httpHead = new AzkabanClient.HttpHead() {
                @Override
                public HttpHeaders builer() {
                    return new HttpHeaders();
                }
            };
            body = azkabanClient.sendPost(httpHead,"/manager?action=create", requestParam);
//            ResponseEntity<String> rs = restTemplate.postForEntity(HOST + "/manager?action=create", httpEntity, String.class);
        } catch (AzkabanClientException ex) {
            ex.printStackTrace();
        } finally {

        }

        return body;
    }

    @Override
    public String deleteProject(String name) {
        String body = "";
        try {
            AzkabanClient.HttpHead httpHead = new AzkabanClient.HttpHead() {
                @Override
                public HttpHeaders builer() {
                    return new HttpHeaders();
                }
            };
            String format = String.format("&delete=true&project=%s", name);
            body = azkabanClient.sendGet(httpHead,"/manager", format);
        } catch (AzkabanClientException ex) {
            log.error(ex.getMessage());
        } finally {

        }
        return body;
    }

    @Override
    public String uploadZip(String project, File file) {
        FileSystemResource resource = new FileSystemResource(file);
        LinkedMultiValueMap<String, Object> linkedMultiValueMap = new LinkedMultiValueMap<String, Object>();
//        linkedMultiValueMap.add("session.id", login());
        linkedMultiValueMap.add("ajax", "upload");
        linkedMultiValueMap.add("project", project);
        linkedMultiValueMap.add("file", resource);
        String rs="";
        try {
            AzkabanClient.HttpHead httpHead = new AzkabanClient.HttpHead() {
                @Override
                public HttpHeaders builer() {
                    return new HttpHeaders();
                }
            };
            rs= azkabanClient.sendPost(httpHead,"/manager",linkedMultiValueMap);
        }catch (AzkabanClientException ex){
            log.error(ex.getMessage());
        }finally {

        }
        return rs;
    }


}
