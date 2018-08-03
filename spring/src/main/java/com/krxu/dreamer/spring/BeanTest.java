package com.krxu.dreamer.spring;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/2
 * @description [添加描述]
 */
public class BeanTest {

    @Value("${LAJIWANGDA}")
    private String LAJIWANGDA;

    public void print(){
        System.out.println(">>>>" + LAJIWANGDA);
    }
}
