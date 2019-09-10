package com.nick.weixx.cloud.az.provider.service;

import java.io.File;

public interface IProjectsService {

    /**
     * 创建project
     * @param name
     * @param description
     * @return
     */
    public String createProject( String name,String description);
    public String deleteProject(String name);
    public String uploadZip(String project, File file);

//    public List<Projects> getAllProjects();
}
