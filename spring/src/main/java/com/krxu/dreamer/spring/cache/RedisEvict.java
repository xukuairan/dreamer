package com.krxu.dreamer.spring.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Administrator
 * @version [版本号]
 * @date 2018/8/18
 * @description [添加描述]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RedisEvict {

    String fieldKey();

    Class type();
}
