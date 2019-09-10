package com.nick.weixx.cloud.az.provider.controller;

import com.nick.weixx.cloud.az.provider.service.IProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("az/project")
public class AzkabanProjectContronller {

    @Autowired
    IProjectsService proService;


    @PostMapping("create")
    public String createProject(@RequestParam("name") String name, @RequestParam("description") String description) {
        String body = proService.createProject(name, description);
        return body;
//        JsonObject obj = new JsonParser().parse(body).getAsJsonObject();
//        if ("success".equals(obj.get("status").getAsString())) {
//            return true;
//        } else {
//            System.out.print(obj.get("message").toString());
//            return false;
//        }
    }

    @GetMapping("del")
    public String delProject(@RequestParam("name") String name) {
        String body = proService.deleteProject(name);
        return body;
    }

    @PostMapping("upload")
    public String uploadZip(@RequestParam("name") String name, @RequestParam(value = "file", required = true) MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        //文件存储路径
        File fileToSave = new File(file.getOriginalFilename());
        FileCopyUtils.copy(bytes, fileToSave);
        return proService.uploadZip(name, fileToSave);
    }


}
