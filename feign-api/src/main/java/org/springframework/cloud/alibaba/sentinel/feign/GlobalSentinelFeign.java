package org.springframework.cloud.alibaba.sentinel.feign;

import com.alibaba.cloud.sentinel.feign.SentinelContractHolder;
import com.alibaba.cloud.sentinel.feign.SentinelInvocationHandler;
import com.alibaba.cloud.sentinel.feign.SentinelTargeterAspect;
import com.szl.feignapi.demo.config.globalFallback.GlobalFallbackFactory;
import feign.Contract;
import feign.Feign;
import feign.InvocationHandlerFactory;
import feign.Target;
import feign.hystrix.FallbackFactory;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.cloud.openfeign.FeignContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @ClassName: GlobalSentinelFeign
 * @Author: SZL
 * @Date: 2022/3/27 16:16
 * @Description: 重新实现SentinelFeign，由于没有对SentinelInvocationHandler的访问权限，我们得在自己项目里建立
 *               org.springframework.cloud.alibaba.sentinel.feign包并将新的SentinelFeign放在此包下
 * @Version 1.0
 */
public class GlobalSentinelFeign {

    public static GlobalSentinelFeign.Builder builder() {
        return new GlobalSentinelFeign.Builder();
    }

    public static final class Builder extends Feign.Builder
            implements ApplicationContextAware {
        private Contract contract = new Contract.Default();
        private ApplicationContext applicationContext;
        private FeignContext feignContext;

        @Override
        public Feign.Builder invocationHandlerFactory(
                InvocationHandlerFactory invocationHandlerFactory) {
            throw new UnsupportedOperationException();
        }
        @Override
        public GlobalSentinelFeign.Builder contract(Contract contract) {
            this.contract = contract;
            return this;
        }
        @Override
        public Feign build() {
            super.invocationHandlerFactory(new InvocationHandlerFactory() {

                @SneakyThrows
                @Override
                public InvocationHandler create(Target target,
                                                Map<Method, MethodHandler> dispatch) {

                    Object feignClientFactoryBean = SentinelTargeterAspect.getFeignClientFactoryBean();
                    //获取指定的构造方法
                    Constructor<SentinelInvocationHandler> constructor = SentinelInvocationHandler.class.
                            getDeclaredConstructor(Target.class, Map.class, FallbackFactory.class);
                    constructor.setAccessible(true);
                    if (feignClientFactoryBean != null) {

                        Class fallback = (Class) GlobalSentinelFeign.Builder.this.getFieldValue(feignClientFactoryBean, "fallback");
                        Class fallbackFactory = (Class) GlobalSentinelFeign.Builder.this.getFieldValue(feignClientFactoryBean, "fallbackFactory");
                        String beanName = (String) GlobalSentinelFeign.Builder.this.getFieldValue(feignClientFactoryBean, "contextId");
                        if (!StringUtils.hasText(beanName)) {
                            beanName = (String) GlobalSentinelFeign.Builder.this.getFieldValue(feignClientFactoryBean, "name");
                        }

                        if (Void.TYPE != fallback) {
                            Object fallbackInstance = this.getFromContext(beanName, "fallback", fallback, target.type());
                            return constructor.newInstance(target, dispatch, new feign.hystrix.FallbackFactory.Default(fallbackInstance));
                        }

                        if (Void.TYPE != fallbackFactory) {
                            feign.hystrix.FallbackFactory fallbackFactoryInstance = (feign.hystrix.FallbackFactory)this.getFromContext(beanName, "fallbackFactory", fallbackFactory, FallbackFactory.class);
                            return constructor.newInstance(target, dispatch, fallbackFactoryInstance);
                        }
                    }

                    // 默认的 fallbackFactory
                    GlobalFallbackFactory funFallbackFactory = new GlobalFallbackFactory(target);
                    return constructor.newInstance(target, dispatch, funFallbackFactory);
                }

                private Object getFromContext(String name, String type,
                                              Class fallbackType, Class targetType) {
                    Object fallbackInstance = feignContext.getInstance(name,
                            fallbackType);
                    if (fallbackInstance == null) {
                        throw new IllegalStateException(String.format(
                                "No %s instance of type %s found for feign client %s",
                                type, fallbackType, name));
                    }

                    if (!targetType.isAssignableFrom(fallbackType)) {
                        throw new IllegalStateException(String.format(
                                "Incompatible %s instance. Fallback/fallbackFactory of type %s is not assignable to %s for feign client %s",
                                type, fallbackType, targetType, name));
                    }
                    return fallbackInstance;
                }
            });
            super.contract(new SentinelContractHolder(contract));
            return super.build();
        }
        private Object getFieldValue(Object instance, String fieldName) {
            Field field = ReflectionUtils.findField(instance.getClass(), fieldName);
            field.setAccessible(true);
            try {
                return field.get(instance);
            } catch (IllegalAccessException e) {
                // ignore
            }
            return null;
        }
        @Override
        public void setApplicationContext(ApplicationContext applicationContext)
                throws BeansException {
            this.applicationContext = applicationContext;
            this.feignContext = this.applicationContext.getBean(FeignContext.class);
        }
    }
}
