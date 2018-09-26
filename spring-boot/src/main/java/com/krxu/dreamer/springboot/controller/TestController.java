package com.krxu.dreamer.springboot.controller;

import com.krxu.dreamer.springboot.dubbo.TestDubboService;
import com.krxu.dreamer.springboot.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/9/18
 * @description [添加描述]
 */
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @RequestMapping("test")
    public TestVO test(HttpServletRequest request) {
        //redis
        String key = "1:spring-boot-redis";
        ValueOperations<Object, Object>  lo = redisTemplate.opsForValue();
        TestVO testVO = (TestVO) lo.get(key);
        if(null == testVO){
            System.out.println("redis no data   ");
            testVO = new TestVO();
            testVO.setId(new Random().nextInt());
            testVO.setUid(testService.getID());
            lo.set(key, testVO);
        }

        return testVO;
    }

}
