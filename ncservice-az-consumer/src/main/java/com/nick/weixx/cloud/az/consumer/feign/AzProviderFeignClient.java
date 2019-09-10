package com.nick.weixx.cloud.az.consumer.feign;

import com.nick.weixx.cloud.az.entity.Projects;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "ncservice-az-proviter", fallback = FeignClientFallback.class)
public interface AzProviderFeignClient {
//    @RequestMapping(method = RequestMethod.GET)
//    @RequestMapping("/az/project/create")
    @RequestMapping(method=RequestMethod.POST,path ="/az/project/create")
    public String createProject(@RequestParam("name") String name, @RequestParam("description") String description);
}

/**
 * 回退信息，错误处理
 */
@Component
class FeignClientFallback implements AzProviderFeignClient {

    @Override
    public String createProject(String name, String description) {
        return null;
    }
}
