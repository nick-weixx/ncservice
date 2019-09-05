package com.nick.weixx.cloud.az.consumer.feign;

import com.nick.weixx.cloud.az.consumer.entity.Projects;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "ncservice-az-proviter", fallback = FeignClientFallback.class)
public interface AzProviderFeignClient {
//    @RequestMapping(method = RequestMethod.GET)
    @GetMapping("/projects/all")
    public List<Projects> getAllProjects();
}

/**
 * 回退信息，错误处理
 */
@Component
class FeignClientFallback implements AzProviderFeignClient {

    @Override
    public List<Projects> getAllProjects() {
        List<Projects> pros = new ArrayList<>();
        return pros;
    }
}
