package com.szl.feignapi.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @ClassName: GlobalFallbackClientTest
 * @Author: SZL
 * @Date: 2022/3/27 16:58
 * @Description: TODO
 * @Version 1.0
 */
@FeignClient(value = "core-service-producer",contextId = "globalFallbackClientTest")
public interface GlobalFallbackClientTest {

    @GetMapping(value = "/globalFallback/{param}")
    String globalFallback(@PathVariable String param);
}
