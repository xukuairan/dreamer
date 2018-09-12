package com.krxu.dreamer.spring;

import com.krxu.dreamer.spring.dao.entity.Content;
import com.krxu.dreamer.spring.service.ContentService;
import com.krxu.dreamer.spring.service.impl.ContentServiceImpl;
import com.krxu.dreamer.spring.task.JokeJiCrawlTask;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xukuairan
 * @version [版本号]
 * @date ${date}
 * @description [添加描述]
 */
public class SpringApp {

    public static void main(String[] args) {
        System.out.println(">>>>>>>>>>>>>>>>  application start");
        ClassPathXmlApplicationContext context = null;
        try {
            context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (null != context) {
            System.out.println(">>>>>>>>>>>>>>>>  application start success");
        } else {
            System.out.println(">>>>>>>>>>>>>>>>  application start failed");
            System.exit(10086);
        }
//        ContentService contentService = context.getBean(ContentServiceImpl.class);
//        Long start = System.currentTimeMillis();
//        System.out.println(contentService.getContent(3620L,null));
//        System.out.println(System.currentTimeMillis() - start);



    }
}
