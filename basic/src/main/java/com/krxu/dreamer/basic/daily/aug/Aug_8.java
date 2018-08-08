package com.krxu.dreamer.basic.daily.aug;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/8
 * @description:
 * 冒泡排序算法
 */
public class Aug_8 {

    public static void main(String[] args) {
        int[] numbers = {2, 1, 1, 2, 2, 4, 56, 734, 4, 8, 9};
        for(int i = 0 ; i < numbers.length ; i ++){
            for(int j = i + 1 ; j < numbers.length ; j ++){
                if(numbers[i] < numbers[j]){
                    int temp = numbers[i];
                    numbers[i] = numbers[j];
                    numbers[j] = temp;
                }
            }
        }
    }

}
