package com.nick.weixx.cloud.az.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


@SpringBootApplication
@EnableZuulProxy
public class NcserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NcserviceApplication.class);
    }
}
