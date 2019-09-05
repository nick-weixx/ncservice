package com.nick.weixx.cloud.az.provider.service.impl;

import com.nick.weixx.cloud.az.provider.dao.IProjectsDao;
import com.nick.weixx.cloud.az.provider.entity.Projects;
import com.nick.weixx.cloud.az.provider.service.IProjectsService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

@Service
public class ProjectsService implements IProjectsService {
    @Autowired
    private IProjectsDao proDao;

    @Override
    public List<Projects> getAllProjects() {
        return proDao.getAllProjects();
    }
}
