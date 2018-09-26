package com.krxu.dreamer.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/9/18
 * @description [添加描述]
 */
@SpringBootApplication
@ServletComponentScan
@EnableCaching
public class AppStarter extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AppStarter.class, args);
    }
}
