package com.krxu.dreamer.basic;

import java.util.Arrays;

public class Sort {


    public static void main(String[] args) {
        int[] a = {2,1,1,2};
        quickSort(a, 0, a.length - 1);
        System.out.println(Arrays.toString(a));

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
