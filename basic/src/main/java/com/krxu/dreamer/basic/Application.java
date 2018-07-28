package com.krxu.dreamer.basic;

import java.util.*;

/**
 * @author xukuairan
 * @version xxx
 * @description 添加类描述
 * @date %date%
 */
public class Application {

    public static void main(String[] args) {
        int[] result = mathAbs(0, 15,14);

        for(int i: result){
            System.out.println(i);
        }
    }

    /**
     * 随机数
     *
     * @param min 随机的最小值
     * @param max 随机的最大值
     * @param n   随机数个数
     * @return
     */
    public static int[] mathAbs(int min, int max, int n) {
        int len = max - min + 1;

        //初始化给定范围的待选数组
        int[] source = new int[len];
        for (int i = min; i < min + len; i++) {
            source[i - min] = i;
        }

        int[] result = new int[n];
        Random rd = new Random();
        int index = 0;
        for (int i = 0; i < result.length; i++) {
            //待选数组0到(len-2)随机一个下标
            index = Math.abs(rd.nextInt() % len--);
            //将随机到的数放入结果集
            result[i] = source[index];
            //将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换
            source[index] = source[len];
        }
        return result;
    }

}

