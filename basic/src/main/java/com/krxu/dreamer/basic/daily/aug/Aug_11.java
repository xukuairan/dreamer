package com.krxu.dreamer.basic.daily.aug;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/13
 * @description [添加描述]
 * 对于一个数组，实现一个函数使得所有奇数位于数组的前半部分，偶数位于数组的后半部分。
 * 思路：
 *     可以使用双指针的方式，一个指针指向数组的开始，一个指针指向数组的尾部，
 *     如果头部的数为偶数且尾部的数是奇数则交换，否则头部指针向后移动，尾部指针向前移动，直到两个指针相遇
 */
public class Aug_11 {

    @Test
    public void test(){
        int[] array = {2,4,6,8,1,3,5,7,12,13,14};
        changeArray(array);
        System.out.println(Arrays.toString(array));
    }


    private static void changeArray(int[] array){
        int length = array.length;
        int left = 0;
        int right = length - 1;
        while(left < right){
            //左奇右偶
            if(isOddNumber(array[left]) && !isOddNumber(array[right])){
                left ++;
                right --;
            }else if(isOddNumber(array[left]) && isOddNumber(array[right])){
                //左奇右奇
                left ++;
            }else if(!isOddNumber(array[left]) && !isOddNumber(array[right])){
                //左偶右偶
                right --;
            }else {
                //左偶右奇
                int temp = array[left];
                array[left] = array[right];
                array[right] = temp;
                left ++;
                right --;
            }
        }
    }

    /**
     * 是否是奇数
     * @param num
     * @return
     */
    private static boolean isOddNumber(int num){
        if(num % 2 == 0){
            return false;
        }
        return true;
    }
}
