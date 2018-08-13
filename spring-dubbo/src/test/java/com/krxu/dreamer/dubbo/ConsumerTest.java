package com.krxu.dreamer.dubbo;

import com.krxu.dreamer.api.ServiceApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author Administrator
 * @version [版本号]
 * @date 2018/8/9
 * @description [添加描述]
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-dubbo-consumer.xml"})
public class ConsumerTest {

    @Resource
    private ServiceApi serviceApi;

    @Test
    public void test(){
        String result = serviceApi.getUserName("xxxx");
        System.out.println(result);
    }
}
