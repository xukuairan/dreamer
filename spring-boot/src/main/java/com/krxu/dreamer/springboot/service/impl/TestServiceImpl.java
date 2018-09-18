package com.krxu.dreamer.springboot.service.impl;

import com.krxu.dreamer.springboot.service.TestService;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/9/18
 * @description [添加描述]
 */
@Service
public class TestServiceImpl implements TestService {

    @Override
    public String getID() {
        return UUID.randomUUID().toString();
    }
}
