package com.krxu.dreamer.springboot.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/9/20
 * @description [添加描述]
 */
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return super.keyGenerator();
    }

}
