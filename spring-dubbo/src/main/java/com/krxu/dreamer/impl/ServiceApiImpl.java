package com.krxu.dreamer.impl;

import com.krxu.dreamer.api.ServiceApi;

/**
 * @author Administrator
 * @version [版本号]
 * @date 2018/8/9
 * @description [添加描述]
 */
public class ServiceApiImpl implements ServiceApi {
    @Override
    public String getUserName(String name) {
        return "wonderful:" + name;
    }
}
