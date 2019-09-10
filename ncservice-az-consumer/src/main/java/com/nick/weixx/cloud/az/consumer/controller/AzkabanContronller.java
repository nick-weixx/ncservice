package com.nick.weixx.cloud.az.consumer.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nick.weixx.cloud.az.consumer.feign.AzProviderFeignClient;
import com.nick.weixx.cloud.az.consumer.service.IProjectsService;
import com.nick.weixx.cloud.az.entity.Projects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("az")
@Slf4j
public class AzkabanContronller {
    @Autowired
    AzProviderFeignClient azProviderClient;
    @Autowired
    IProjectsService proService;

    @PostMapping("/create/{name}")
    public String create(@PathVariable String name, @RequestParam("description") String description) {
        String rs = azProviderClient.createProject(name, description);

        JsonObject obj = new JsonParser().parse(rs).getAsJsonObject();
        if ("success".equals(obj.get("status").getAsString())) {
            log.debug("create project success...");
//            return true;
        } else {
            log.error("create project error : {}",obj.get("message").toString());
//            System.out.print(obj.get("message").toString());
//            return false;
        }

        return rs;
    }

    @GetMapping("/all")
    public List<Projects> getAllProjects1() {
        return proService.getAllProjects();
    }
}
