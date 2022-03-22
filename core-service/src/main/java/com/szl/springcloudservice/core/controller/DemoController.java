package com.szl.springcloudservice.core.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright (C), 2021
 *
 * @ClassName: DemoController
 * @Author: SZL
 * @Date: 2021/12/22 22:37
 * @Description: TODO
 * @Version 1.0
 */
@RestController
public class DemoController {

    @Value("${server.port}")
    private int port;


    @Value("${scaffolding.name}")
    private String name;


    @GetMapping(value = "/echo/{param}")
    public String echo(@PathVariable String param) {
        return "Hello Nacos Discovery "  + name + param + port;
    }

    @GetMapping(value = "/echo/currentLimiting/{limiting}")
    public String currentLimiting(@PathVariable String limiting){
        return "limiting"  + name + limiting + port;
    }
}
