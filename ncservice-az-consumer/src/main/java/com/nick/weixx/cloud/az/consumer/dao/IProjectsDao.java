package com.nick.weixx.cloud.az.consumer.dao;

import com.nick.weixx.cloud.az.entity.Projects;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * azkaban projects find
 */
@Mapper
public interface IProjectsDao {

    @Select("select name,active,last_modified_by from projects")
    @Results({
            @Result(property = "name", column = "name"),
            @Result(property = "active", column = "active"),
            @Result(property = "lastModifiedBy", column = "last_modified_by"),

    })
    public  List<Projects> getAllProjects();

}
