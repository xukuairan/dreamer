package com.krxu.dreamer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Administrator
 * @version [版本号]
 * @date 2018/8/9
 * @description [添加描述]
 */
public class AppSpringDubbo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(">>>>>>>>>>>>>>>>  application start");
        ClassPathXmlApplicationContext context = null;
        try {
            context = new ClassPathXmlApplicationContext("classpath:springContext.xml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (null != context) {
            System.out.println(">>>>>>>>>>>>>>>>  application start success");
        } else {
            System.out.println(">>>>>>>>>>>>>>>>  application start failed");
            System.exit(10086);
        }

        while (true){
            Thread.sleep(5000);
        }
    }
}
