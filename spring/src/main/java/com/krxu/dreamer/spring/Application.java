package com.krxu.dreamer.spring;

import com.krxu.dreamer.redis.manager.RedisCacheManager;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.UUID;

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
            System.exit(10086);
        }

        BeanTest t = (BeanTest) context.getBean("beanTest");
        t.print();

        System.out.println(System.getProperty("LAJIWANGDA"));

//        RedisCacheManager redisCacheManager = (RedisCacheManager) context.getBean("redisCacheManager");
//
//        User user = new User();
//        user.setId(924);
//        user.setName("xukuairan");
//        redisCacheManager.set("1:2:3", user, 120);
//
//        long s = System.currentTimeMillis();
//        for(int i = 0 ; i < 100 ; i++){
//            redisCacheManager.set("1:" + UUID.randomUUID().toString(), user,50);
//        }
//
//        System.out.println(System.currentTimeMillis() - s);


    }
}
