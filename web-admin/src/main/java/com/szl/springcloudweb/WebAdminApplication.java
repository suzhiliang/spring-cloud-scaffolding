package com.szl.springcloudweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.szl.*")
@EnableFeignClients(basePackages = "com.szl.feignapi.*")
public class WebAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebAdminApplication.class, args);
    }

}
