package com.krxu.dreamer.springboot.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.krxu.dreamer.springboot.dubbo.TestDubboService;

import java.util.Random;
import java.util.UUID;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/9/18
 * @description [添加描述]
 */
@Service(version = "1.0.0")
public class TestDubboServiceImpl implements TestDubboService {

    @Override
    public String getID() {
        return UUID.randomUUID().toString();
    }

    @Override
    public int getIntId() {
        return new Random().nextInt();
    }
}
