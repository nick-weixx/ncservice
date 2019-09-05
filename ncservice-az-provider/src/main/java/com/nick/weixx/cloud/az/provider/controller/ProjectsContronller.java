package com.nick.weixx.cloud.az.provider.controller;

import com.nick.weixx.cloud.az.provider.entity.Projects;
import com.nick.weixx.cloud.az.provider.service.IProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("projects")
public class ProjectsContronller {
    @Autowired
    IProjectsService proService;

    @GetMapping("/all")
    public List<Projects> getAllProjects() {
        return proService.getAllProjects();
    }

}
