package com.krxu.dreamer.springboot.controller;

import lombok.Data;

import java.io.Serializable;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/9/18
 * @description [添加描述]
 */
@Data
public class TestVO implements Serializable {
    private int id ;
    private String uid;
    private String dId;
}
