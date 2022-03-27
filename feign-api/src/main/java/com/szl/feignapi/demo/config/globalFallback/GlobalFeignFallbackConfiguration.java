package com.szl.feignapi.demo.config.globalFallback;

import com.alibaba.cloud.sentinel.feign.SentinelFeign;
import com.alibaba.csp.sentinel.SphU;
import feign.Feign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.alibaba.sentinel.feign.GlobalSentinelFeign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

/**
 * @ClassName: GlobalFeignFallbackConfiguration
 * @Author: SZL
 * @Date: 2022/3/27 17:43
 * @Description: 全局fallBack配置
 * @Version 1.0
 */
@Configuration
public class GlobalFeignFallbackConfiguration {

    @Bean
    @Scope("prototype")
    @ConditionalOnClass({SphU.class, Feign.class})
    @ConditionalOnProperty(name = "feign.sentinel.enabled")
    @Primary
    public Feign.Builder feignSentinelBuilder() {
        return GlobalSentinelFeign.builder();
    }

}
