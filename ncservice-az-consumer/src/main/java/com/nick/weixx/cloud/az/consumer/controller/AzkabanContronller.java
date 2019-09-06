package com.nick.weixx.cloud.az.consumer.controller;

import com.nick.weixx.cloud.az.consumer.feign.AzProviderFeignClient;
import com.nick.weixx.cloud.az.entity.Projects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("az")
public class AzkabanContronller {
    @Autowired
    AzProviderFeignClient azProviderClient;
    @GetMapping("/project/all")
    public List<Projects> getAllProjects() {
        return azProviderClient.getAllProjects();
    }
}
