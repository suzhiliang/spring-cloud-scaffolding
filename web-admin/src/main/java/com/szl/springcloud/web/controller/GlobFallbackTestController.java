package com.szl.springcloud.web.controller;

import com.szl.feignapi.demo.GlobalFallbackClientTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: GlobFallbackTestController
 * @Author: SZL
 * @Date: 2022/3/27 16:55
 * @Description: 全局服务降级方法测试
 * @Version 1.0
 */
@RestController
@RequestMapping("/globFallbackTest")
public class GlobFallbackTestController {

    @Autowired
    private GlobalFallbackClientTest globalFallbagckClientTest;

    @GetMapping("/test")
    public String globalFallbagckClientTest() {
       return globalFallbagckClientTest.globalFallback("你答應");
    }

}
