package com.nick.weixx.cloud.az.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient//soa服务配置,@EnableEurekaClient与之相同，注册eureka作微服务
@EnableFeignClients//soa注解调用服务配置器
@EnableCircuitBreaker//断路器
public class AzConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AzConsumerApplication.class);
    }
}
