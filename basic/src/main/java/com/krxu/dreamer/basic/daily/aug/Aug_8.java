package com.krxu.dreamer.basic.daily.aug;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/8
 * @description:
 * 冒泡排序算法
 */
public class Aug_8 {

    @Test
    public void testBubbleSort(){
        int[] numbers = {2, 1, 3, 12, 11, 6, 56, 734, 4, 8, 9};
        System.out.println(Arrays.toString(numbers));
    }

    public static void bubbleSort(int[] numbers){
        for(int i = 0 ; i < numbers.length ; i ++){
            for(int j = i + 1 ; j < numbers.length ; j ++){
                if(numbers[i] > numbers[j]){
                    int temp = numbers[i];
                    numbers[i] = numbers[j];
                    numbers[j] = temp;
                }
                System.out.println(Arrays.toString(numbers));
            }
        }
    }
}
