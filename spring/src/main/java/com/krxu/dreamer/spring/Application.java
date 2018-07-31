package com.krxu.dreamer.spring;

import com.krxu.dreamer.redis.manager.RedisCacheManager;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author xukuairan
 * @version [版本号]
 * @date ${date}
 * @description [添加描述]
 */
public class Application {

    public static void main(String[] args) {
        System.out.println(">>>>>>>>>>>>>>>>  application start");
        ClassPathXmlApplicationContext context = null;
        try {
            context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (null != context) {
            System.out.println(">>>>>>>>>>>>>>>>  application start success");
        } else {
            System.out.println(">>>>>>>>>>>>>>>>  application start failed");
        }

        RedisCacheManager redisCacheManager = (RedisCacheManager) context.getBean("redisCacheManager");

        User user = new User();
        user.setId(924);
        user.setName("xukuairan");
        redisCacheManager.set("1:2:3", user,50);

        User cacheUser = redisCacheManager.get("1:2:3", User.class);
        System.out.println(cacheUser);

    }
}
