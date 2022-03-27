package com.szl.feignapi.demo.config.globalFallback;

import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @ClassName: GlobalFeignFallback
 * @Author: SZL
 * @Date: 2022/3/27 16:05
 * @Description: 全局服务降级函数 ：编写真正的Fallback处理器
 * @Version 1.0
 */
@Slf4j
@AllArgsConstructor
public class GlobalFeignFallback<T> implements MethodInterceptor {
    private final Class<T> targetType;
    private final String targetName;
    private final Throwable cause;

    @Nullable
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) {
        String errorMessage = cause.getMessage();
        log.error("FunFeignFallback:[{}.{}] serviceId:[{}] message:[{}]", targetType.getName(), method.getName(), targetName, errorMessage);
        // 非 FeignException，直接返回
        if (!(cause instanceof FeignException)) {
            //此处只是示例，具体可以返回带有业务错误数据的对象
            return errorMessage;
        }
        FeignException exception = (FeignException) cause;
        //此处只是示例，具体可以返回带有业务错误数据的对象
        return exception.contentUTF8();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GlobalFeignFallback<?> that = (GlobalFeignFallback<?>) o;
        return targetType.equals(that.targetType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetType);
    }
}
