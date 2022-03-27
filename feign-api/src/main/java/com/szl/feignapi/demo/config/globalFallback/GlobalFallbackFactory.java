package com.szl.feignapi.demo.config.globalFallback;

import feign.Target;
import feign.hystrix.FallbackFactory;
import lombok.AllArgsConstructor;
import org.springframework.cglib.proxy.Enhancer;

/**
 * @ClassName: FunFallbackFactory
 * @Author: SZL
 * @Date: 2022/3/27 16:05
 * @Description: 全局服务降级函数 ：编写FallbackFactory
 * @Version 1.0
 */
@AllArgsConstructor
public class GlobalFallbackFactory<T> implements FallbackFactory<T> {

    private final Target<T> target;

    @Override
    @SuppressWarnings("unchecked")
    public T create(Throwable cause) {
        final Class<T> targetType = target.type();
        final String targetName = target.name();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetType);
        enhancer.setUseCache(true);
        enhancer.setCallback(new GlobalFeignFallback<>(targetType, targetName, cause));
        return (T) enhancer.create();
    }
}
