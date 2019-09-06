package com.nick.weixx.cloud.az.provider.config;

import com.nick.weixx.cloud.az.provider.utils.AzkabanClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AzkabanConfig {
    @Value("${azkaban.host}")
    String HOST;
    @Value("${azkaban.username}")
    String USER_NAME;
    @Value("${azkaban.password}")
    String PASS_WORD;

    @Autowired
    RestTemplate restTemplate;


    @Bean(name = "azkabanClient")
    public AzkabanClient createClient(){
        return  new AzkabanClient(restTemplate,HOST,USER_NAME,PASS_WORD);
    }


//    public String send(final LinkedMultiValueMap<String, String> requestParam, String action) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
//        headers.add("X-Requested-With", "XMLHttpRequest");
//        HttpEntity<LinkedMultiValueMap<String, String>> httpEntity = new HttpEntity<>(requestParam, headers);
//        String body = "";
//        try {
//            ResponseEntity<String> rs = restTemplate.postForEntity(HOST + action, httpEntity, String.class);
//            body = rs.getBody();
//        } catch (ResourceAccessException ex) {
//            if (ex.getMessage().indexOf("Connection refused") != -1) {
//                System.out.println("azkaban 服务连接异常...");
//            }
////            ex.printStackTrace();
//        } finally {
//
//        }
//        return body;
//    }
}
