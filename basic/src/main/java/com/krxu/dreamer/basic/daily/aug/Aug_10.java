package com.krxu.dreamer.basic.daily.aug;

import java.util.Arrays;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/10
 * @description: 堆排序
 */
public class Aug_10 {

    public static void main(String[] args) {
        int[] a = {5, 2, 4, 7, 9, 3, 1};
        int arrayLength = a.length;
        //循环建堆
        for (int i = 0; i < arrayLength - 1; i++) {
            //建堆
            buildMaxHeap(a, arrayLength - 1 - i);
            //交换堆顶和最后一个元素
            swap(a, 0, arrayLength - 1 - i);
            System.out.println(Arrays.toString(a));
            System.out.println("------------------------");
        }
    }

    /**
     * 对data数组从0到 elements 建大顶堆
     *
     * @param data
     * @param elements
     */
    public static void buildMaxHeap(int[] data, int elements) {
        //最后一个节点的父节点开始
        int lastNode = (elements - 1) / 2;
        for (int i = lastNode; i >= 0; i--) {
            //正在判断的节点
            int nodeIndex = i;

            //如果当前k节点的子节点存在,biggerIndex = k节点的左子节点的索引
            while (nodeIndex * 2 + 1 <= elements) {
                int biggerIndex = nodeIndex * 2 + 1;

                //如果biggerIndex小于lastIndex，即biggerIndex+1代表的k节点的右子节点存在
                if (biggerIndex < elements) {
                    //若果右子节点的值较大
                    if (data[biggerIndex] < data[biggerIndex + 1]) {
                        //biggerIndex总是记录较大子节点的索引
                        biggerIndex++;
                    }
                }
                //如果节点的值小于其较大的子节点的值
                if (data[nodeIndex] < data[biggerIndex]) {
                    //交换他们
                    swap(data, nodeIndex, biggerIndex);
                    //将biggerIndex赋予k，开始while循环的下一次循环，重新保证k节点的值大于其左右子节点的值
                    nodeIndex = biggerIndex;
                } else {
                    break;
                }
            }
        }
        System.out.println("build max heap :" + Arrays.toString(data));
    }

    private static void swap(int[] data, int i, int j) {
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }
}
