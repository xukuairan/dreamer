package com.krxu.dreamer.basic.daily.aug;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/9
 * @description:
 * 直接插入排序
 */
public class Aug_9 {

    @Test
    public void testInsertionSort(){
        int[] numbers = {2, 1, 3, 12, 11, 6, 56, 734, 4, 8, 9};
        insertionSort(numbers);
    }

    public static void insertionSort(int[] numbers){
        for(int index = 1 ; index < numbers.length ; index ++ ){
            //注意[0,i-1]都是有序的。如果待插入元素比arr[i-1]还大则无需再与[i-1]前面的元素进行比较了，反之则进入if语句
            if(numbers[index] < numbers[index-1]){
                int temp = numbers[index];
                int j;
                for(j = index-1; j >= 0 && numbers[j] > temp; j --){
                    //把比temp大或相等的元素全部往后移动一个位置
                    numbers[j+1] = numbers[j];
                }
                //把待排序的元素temp插入腾出位置的(j+1)
                numbers[j+1] = temp;
            }
            System.out.println(Arrays.toString(numbers));
        }


    }
}
