package com.krxu.dreamer.basic;

import java.util.Arrays;
import java.util.Random;

public class Sort {
    public static void main(String[] args) {
        int[] a = new int[1000];
        int[] b = new int[1000];
        int[] c = new int[1000];
        Random random = new Random();
        for(int i = 0 ;i < a.length; i ++){
            a[i] =  random.nextInt(1000);
            b[i] =  a[i];
            c[i] =  a[i];
        }
        long _1 = System.currentTimeMillis();
        quickSort(a, 0, a.length - 1);

        long _2 = System.currentTimeMillis();
        System.out.println("quickSort：" + (_2 - _1) + "ms");
        System.out.println(Arrays.toString(a));
        bubbleSort(b);
        long _3 = System.currentTimeMillis();
        System.out.println("bubbleSort：" + (_3 - _2) + "ms");


        insertionSort(c);
        long _4 = System.currentTimeMillis();
        System.out.println("insertionSort：" + (_4 - _3) + "ms");
    }

    /**
     * 冒泡排序
     * @param numbers
     * 比较相邻的元素。如果第一个比第二个大，就交换他们两个。
     * 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。
     * 针对所有的元素重复以上的步骤，除了最后一个。
     * 持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
     */
    private static void bubbleSort(int[] numbers) {
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
     * 排序思路：每次将一个待排序的元素与已排序的元素进行逐一比较，直到找到合适的位置按大小插入。
     *
     * @param numbers
     */
    private static void insertionSort(int[] numbers){
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
