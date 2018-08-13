package com.krxu.dreamer;

import com.alibaba.dubbo.common.URL;
import org.junit.Test;
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

    @Test
    public void test(){
        String u = "dubbo://192.168.0.105:18888/com.krxu.dreamer.api.ServiceApi?anyhost\n" +
                "=true&application=dubbo-provider-demo&dubbo=2.5.3&interface=com.kr\n" +
                "xu.dreamer.api.ServiceApi&methods=getUserName&organization=project&own\n" +
                "er=xukuairan&pid=956&revision=1.0.0&side=provider&timestamp=15\n" +
                "33912233266&version=1.0.0";
        URL url = URL.valueOf(u);
        System.out.println(url);
    }
}
