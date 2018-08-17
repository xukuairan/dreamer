package com.krxu.dreamer.basic.daily.aug;

import org.junit.Test;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/17
 * @description [添加描述]
 * <p>
 * 输入一个整型数组，数组里有正数也有负数。数组中一个或者连续的多个整数组成一个字数组。求所有字数组的和的最大值。要求时间复杂度为O(n)。
 */
public class Aug_17 {

    @Test
    public void test() {
        int[] array = {3, -2, 3, 10, -4, 7, 2, -5};
        int[] array1 = {-3, -1, -2, -4};
        System.out.println(findGreatestSumOfSubArray(array));
        System.out.println(findGreatestSumOfSubArray(array1));

    }

    public static int findGreatestSumOfSubArray(int[] array) {
        //当前和
        int currentSum = 0;
        //-2147483648
        int greatestSum = 0x80000000;
        for (int i = 0; i < array.length; i++) {
            if(currentSum < 0){
                currentSum = array[i];
            }else{
                currentSum += array[i];
            }
            if(currentSum > greatestSum){
                greatestSum = currentSum;
            }
        }
        return greatestSum;
    }

}
