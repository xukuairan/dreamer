package com.krxu.dreamer.springboot.controller;

import com.krxu.dreamer.springboot.dubbo.TestDubboService;
import com.krxu.dreamer.springboot.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("test")
    public TestVO test(){
        TestVO testVO = new TestVO();
        testVO.setId(new Random().nextInt());
        testVO.setUid(testService.getID());
        return testVO;
    }

}
