package com.krxu.dreamer.basic;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author xukuairan
 * @version xxx
 * @description 添加类描述
 * @date %date%
 */
public class Application {

    public static void main(String[] args) {
        int count = 2;
        int allCount = 11;
        BigDecimal a = new BigDecimal(count);
        BigDecimal b = new BigDecimal(allCount);
        BigDecimal c = a.divide(b, 4, RoundingMode.HALF_UP);
        System.out.println(c.toString());
    }
}

