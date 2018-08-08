package com.krxu.dreamer.basic;

import java.util.Arrays;

public class Sort {
    public static void main(String[] args) {
        int[] a = {2, 1, 1, 2,2,4,56,734,4,8,9};
        quickSort(a, 0, a.length - 1);
        System.out.println(Arrays.toString(a));
        bubbleSort(a);
    }

    /**
     * 冒泡排序
     * @param numbers
     * 比较相邻的元素。如果第一个比第二个大，就交换他们两个。
     * 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。
     * 针对所有的元素重复以上的步骤，除了最后一个。
     * 持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
     */
    public static void bubbleSort(int[] numbers) {
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


    /**
     * 快速排序
     *
     * @param a
     * @param low
     * @param high
     */
    private static void quickSort(int[] a, int low, int high) {
        if (low > high) {
            return;
        }
        int i = low;
        int j = high;
        int key = a[low];
        while (i < j) {
            while (i < j && a[j] > key) {
                j--;
            }
            while (i < j && a[i] <= key) {
                i++;
            }
            if (i < j) {
                int p = a[i];
                a[i] = a[j];
                a[j] = p;
            }
        }
        int p = a[i];
        a[i] = a[low];
        a[low] = p;
        quickSort(a, low, i - 1);
        quickSort(a, i + 1, high);
    }
}
