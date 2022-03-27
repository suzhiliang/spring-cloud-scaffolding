package com.szl.feignapi.demo;

import com.szl.feignapi.demo.fallback.DemoFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @ClassName: DemoFeignClient
 * @Author: SZL
 * @Date: 2021/12/24 16:25
 * @Description: TODO
 * @Version 1.0
 */
@FeignClient(value = "core-service-producer",contextId = "demoFeignClient",fallback = DemoFeignClientFallback.class)
public interface DemoFeignClient {

    @GetMapping(value = "/echo/{param}")
    String echo(@PathVariable String param);


    @GetMapping(value = "/echo/currentLimiting/{limiting}")
    String currentLimiting(@PathVariable String limiting);
}
