package com.krxu.dreamer.spring.cache;

import com.krxu.dreamer.redis.manager.RedisCacheManager;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Administrator
 * @version [版本号]
 * @date 2018/8/18
 * @description [添加描述]
 */
@Log4j
@Component
@Aspect
public class CacheAspect {

    @Autowired
    private RedisCacheManager redisCacheManager;

    @Around("@annotation(com.krxu.dreamer.spring.cache.RedisCache)")
    public Object invokeCacheAspect(final ProceedingJoinPoint jp) throws Throwable {
        Signature sig = jp.getSignature();
        MethodSignature methodSignature ;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        methodSignature = (MethodSignature) sig;
        Object target = jp.getTarget();
        Method method = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());

        String prefixKey = method.getAnnotation(RedisCache.class).prefixKey();

        // 得到类名、方法名和参数
        Object[] args = jp.getArgs();

        // 根据类名，方法名和参数生成key[前缀:方法全路径]
        Class targetClass = jp.getTarget().getClass();
        StringBuilder cacheKeySb = new StringBuilder(prefixKey);
        cacheKeySb.append(":");
        cacheKeySb.append(targetClass.getName());
        cacheKeySb.append(".");
        cacheKeySb.append(method.getName());
        cacheKeySb.append(":");

        for(int i = 0; i < args.length ; i ++){
            cacheKeySb.append(args[i]);
            if(i != args.length - 1){
                cacheKeySb.append("_");
            }
        }

        String cacheKey = cacheKeySb.toString();
        if (log.isDebugEnabled()) {
            log.debug("生成key:" + cacheKey);
        }

        //注解的缓存失效时间
        int expire = method.getAnnotation(RedisCache.class).expire();

        // 得到被代理方法的返回值类型
        Class returnType = ((MethodSignature) jp.getSignature()).getReturnType();

        // 检查redis中是否有缓存
        Object redisValue = redisCacheManager.get(cacheKey, returnType);

        if (null == redisValue) {
            if (log.isDebugEnabled()) {
                log.debug("缓存未命中");
            }
            // 调用数据库查询方法
            redisValue = jp.proceed(args);

            //存入缓存
            if(redisValue != null){
                String statusCodeReply = redisCacheManager.set(cacheKey, redisValue, expire);
                if (log.isDebugEnabled()) {
                    log.debug("缓存存入结果 = " + statusCodeReply);
                }
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("缓存命中");
            }
        }

        return redisValue;
    }

    public void setRedisCacheManager(RedisCacheManager redisCacheManager) {
        this.redisCacheManager = redisCacheManager;
    }
}
