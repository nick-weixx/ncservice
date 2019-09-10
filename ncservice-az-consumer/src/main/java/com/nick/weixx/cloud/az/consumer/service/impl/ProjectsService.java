package com.nick.weixx.cloud.az.consumer.service.impl;

import com.nick.weixx.cloud.az.consumer.dao.IProjectsDao;
import com.nick.weixx.cloud.az.consumer.service.IProjectsService;
import com.nick.weixx.cloud.az.entity.Projects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
