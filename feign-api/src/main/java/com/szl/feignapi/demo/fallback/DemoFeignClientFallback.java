package com.szl.feignapi.demo.fallback;

import com.szl.feignapi.demo.DemoFeignClient;
import org.springframework.stereotype.Component;

/**
 * @ClassName: DemoFeginClientFallback
 * @Author: SZL
 * @Date: 2022/3/22 21:08
 * @Description: 服务降级方法
 * @Version 1.0
 */
@Component
public class DemoFeignClientFallback implements DemoFeignClient {

    @Override
    public String echo(String param) {
        return "熔断断了局部服务降级了";
    }

    @Override
    public String currentLimiting(String limiting) {
        return "熔断断了局部服务降级了";
    }
}
