package com.krxu.dreamer.basic;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xukuairan
 * @version xxx
 * @description 添加类描述
 * @date %date%
 */
public class AppBasic {


    public static void main(String[] args) throws InterruptedException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        System.out.println( format.format(new Date(runtimeMXBean.getStartTime())));
        Thread.sleep(5000);
        System.out.println( format.format(new Date(runtimeMXBean.getStartTime())));
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

}

