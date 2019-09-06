package com.nick.weixx.cloud.az.provider.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nick.weixx.cloud.az.entity.Projects;
import com.nick.weixx.cloud.az.provider.service.IProjectsService;
import com.nick.weixx.cloud.az.provider.utils.AzkabanClient;
import com.nick.weixx.cloud.az.provider.utils.AzkabanClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("az/project")
public class AzkabanProjectContronller {
    @Autowired
    IProjectsService proService;
    @Autowired
    AzkabanClient azkabanClient;

    @GetMapping("/all")
    public List<Projects> getAllProjects() {
        return proService.getAllProjects();
    }


    @PostMapping("create/{name}")
    public boolean createProject(@PathVariable String name, @RequestParam("description") String description) {
        LinkedMultiValueMap<String, String> requestParam = new LinkedMultiValueMap<String, String>();
//        requestParam.add("session.id", SESSION_ID);
        requestParam.add("name", name);
        requestParam.add("description", description);

        String body = "";
        try {
            body = azkabanClient.sendPost("/manager?action=create", requestParam);
//            ResponseEntity<String> rs = restTemplate.postForEntity(HOST + "/manager?action=create", httpEntity, String.class);
        } catch (AzkabanClientException ex) {
            ex.printStackTrace();
        } finally {

        }

        JsonObject obj = new JsonParser().parse(body).getAsJsonObject();
        if ("success".equals(obj.get("status").getAsString())) {
            return true;
        } else {
            System.out.print(obj.get("message").toString());
            return false;
        }

    }

}
