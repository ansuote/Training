package com.lkl.ansuote.traning.module.util;

/**
 * https://algs4.cs.princeton.edu/code/
 * https://github.com/MisterBooo/Article
 * @author huangdongqiang
 * @date 23/05/2019
 */
public class ArithMeticUtil {

    /**
     * 交换数组里面 A, B 的位置
     * @param dataList
     * @param i
     * @param j
     */
    public static void swap(int[] dataList, int i, int j) {
        int temp = dataList[i];
        dataList[i] = dataList[j];
        dataList[j] = temp;
    }


    //32, 4, 2, 6, 1, 8, 10, 53, 12, 18

    /**
     * 冒泡排序
     * @param dataList
     */
    public static void bubbleSort(int[] dataList) {
        for (int i = 1; i < dataList.length; i++) {

            boolean isFlag = true;
            for (int j = 0; j < dataList.length - i; j++) {
                if (dataList[j] > dataList[j + 1]) {
                    swap(dataList, j, j + 1);
                    isFlag = false;
                }
            }

            if (isFlag) {
                break;
            }
        }

    }

    /**
     * https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/Quick.java.html
     * @param dataList
     */
    public static void quickSort(int[] dataList) {
        quickSort(dataList, 0, dataList.length -1);
    }

    private static void quickSort(int[] dataList, int left, int right) {
        if (left < right) {
            int partitionIndex = partition(dataList, left, right);
            quickSort(dataList, 0, partitionIndex - 1);
            quickSort(dataList, partitionIndex + 1, right);
        }
    }

    /**
     * 寻找基准点，基准点左边的数据，都小于基准点。基准点右边的数据都大于基准点
     * @param dataList
     * @param left
     * @param right
     * @return
     */
    private static int partition(int[] dataList, int left, int right) {
        int i = left;
        int j = right + 1;
        int pivot = left;

        while (true) {

            while (dataList[++i] < dataList[pivot]) {
                if (i == right) {
                    break;
                }
            }

            while (dataList[--j] > dataList[pivot]) {
                if (j == left) {
                    break;
                }
            }

            if (i >= j) {
                break;
            }

            swap(dataList, i, j);
        }
        swap(dataList, pivot, j);
        return j;
    }

    /**
     * 选择排序
     * @param dataList
     */
    public static void selectionSort(int[] dataList) {
        for (int i = 0; i < dataList.length; i++) {
            int min = i;
            for (int j = i + 1; j < dataList.length; j++) {
                if (dataList[j] < dataList[min]) {
                    min = j;
                }
            }

            if (min != i) {
                swap(dataList, i, min);
            }
        }
    }

    /**
     * 插入排序
     * @param dataList
     */
    public static void insertSort(int[] dataList) {
        for (int i = 1; i < dataList.length; i++) {
            int tmp = dataList[i];

            int j = i;
            while (j > 0 && tmp < dataList[j - 1]) {
                dataList[j] = dataList[j - 1];
                j--;
            }

            if (i != j) {
                dataList[j] = tmp;
            }
        }
    }
}
