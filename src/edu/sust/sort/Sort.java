package edu.sust.sort;

import java.util.List;

/**
 * Created by SunnyGrocery on 2019/10/16 14:56
 */
public class Sort {
    private List<Double> unOrdered;

    /**
     * 冒泡排序
     */
    public void bubbleSort() {
        for (int i = 0; i < unOrdered.size(); i++) {
            for (int j = 0; j < unOrdered.size() - i - 1; j++) {
                double a = unOrdered.get(j);
                double b = unOrdered.get(j + 1);
                if (a - b > 0) {
                    unOrdered.set(j, b);
                    unOrdered.set(j + 1, a);
                }
            }
        }
    }

    /**
     * 选择排序
     */
    public void selectSort() {
        for (int i = 0; i < unOrdered.size(); i++) {
            int minIndex = i;
            double minInt = unOrdered.get(i);
            for (int j = i; j < unOrdered.size() - 1; j++) {
                if (minInt - unOrdered.get(j + 1) > 0) {
                    minIndex = j + 1;
                    minInt = unOrdered.get(minIndex);
                }
            }
            double temp1 = unOrdered.get(minIndex);
            double temp2 = unOrdered.get(i);
            unOrdered.set(i, temp1);
            unOrdered.set(minIndex, temp2);
        }
    }

    /**
     * 插入排序
     */
    public void insertSort() {
        for (int i = 1; i < unOrdered.size(); i++) {
            double willInsert = unOrdered.get(i);
            //在有序列表中寻找插入位置
            for (int j = 0; j <= i - 1; j++) {
                double b = unOrdered.get(j);
                if (willInsert - b < 0) {
                    insertArrayList(j, i);
                    break;
                }
            }
        }
    }

    private void insertArrayList(int insertIndex, int originalIndex) {
        double temp = unOrdered.get(originalIndex);
        for (int i = originalIndex; i > insertIndex; i--) {
            unOrdered.set(i, unOrdered.get(i - 1));
        }
        unOrdered.set(insertIndex, temp);
    }

    /**
     * 希尔排序
     * @param inc 一般用size()/2作为初始值
     */
    public void shellSort(int inc) {
        for (; inc > 0; inc /= 2) {
            for (int i = inc; i < unOrdered.size(); i += inc) {
                double willInsert = unOrdered.get(i);
                int j;
                for (j = i - inc; j >= 0; j -= inc) {
                    if (willInsert - unOrdered.get(j) <= 0) {
                        unOrdered.set(j + inc, unOrdered.get(j));
                    } else {
                        break;
                    }
                }
                unOrdered.set(j + inc, willInsert);
            }
        }
    }

    public List<Double> mergeTemp;

    /**
     * 归并排序
     * @param left 左起始位置
     * @param right 右起始位置
     */
    public void mergeSort(int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(left, mid);
            mergeSort(mid + 1, right);
            merge1(left, right);
        }
    }

    public void merge1(int left, int right) {
        int mid = (left + right) / 2 + 1;
        int tempIndex = left;
        int originalLeft = left;
        int midIndex = mid;
        while (left < midIndex && mid <= right) {
            if (unOrdered.get(left) <= unOrdered.get(mid)) {
                mergeTemp.set(tempIndex, unOrdered.get(left));
                left++;
            } else {
                mergeTemp.set(tempIndex, unOrdered.get(mid));
                mid++;
            }
            tempIndex++;
        }
        while (left < midIndex) {
            mergeTemp.set(tempIndex, unOrdered.get(left));
            left++;
            tempIndex++;
        }
        while (mid <= right) {
            mergeTemp.set(tempIndex, unOrdered.get(mid));
            mid++;
            tempIndex++;
        }
        for (int i = originalLeft; i <= right; i++) {
            unOrdered.set(i, mergeTemp.get(i));
        }
    }

    /**
     * 快速排序
     * @param left 左起始位置
     * @param right 右起始位置
     */
    public void quickSort(int left, int right) {
        if (left < right) {
            int pivot = findPivot(left, right);
            quickSort(left, pivot - 1);
            quickSort(pivot + 1, right);
        }
    }

    private int findPivot(int left, int right) {
        double pivot = unOrdered.get(left);
        while (left != right) {
            while (unOrdered.get(right) >= pivot && left != right) {
                right--;
            }
            unOrdered.set(left, unOrdered.get(right));
            while (unOrdered.get(left) <= pivot && left != right) {
                left++;
            }
            unOrdered.set(right, unOrdered.get(left));

        }
        unOrdered.set(left, pivot);
        return left;
    }

    /**
     * 堆排序
     * I think is just a fake heapSort
     */
    public void heapSort() {
        for (int i = 0; i < unOrdered.size(); i++) {
            for (int j = unOrdered.size(); j / 2 - 1 >= 0; j--) {
                swapMaxHeap(j / 2 - 1, unOrdered.size() - i);
            }
            //交换
            double temp = unOrdered.get(0);
            unOrdered.set(0, unOrdered.get(unOrdered.size() - 1 - i));
            unOrdered.set(unOrdered.size() - 1 - i, temp);
            unOrdered.set(unOrdered.size() - 1 - i, temp);
        }
    }
    //交换最大的到当前
    private void swapMaxHeap(int rootIndex, int length) {
        int lcIndex = rootIndex * 2 + 1;
        int rcIndex = lcIndex + 1;

        double max = unOrdered.get(rootIndex);
        int maxIndex = rootIndex;

        if (lcIndex < length && unOrdered.get(lcIndex) > unOrdered.get(maxIndex)) {
            max = unOrdered.get(lcIndex);
            maxIndex = lcIndex;
        }
        if (rcIndex < length && unOrdered.get(rcIndex) > unOrdered.get(maxIndex)) {
            max = unOrdered.get(rcIndex);
            maxIndex = rcIndex;
        }
        if (maxIndex != rootIndex) {
            unOrdered.set(maxIndex, unOrdered.get(rootIndex));
            unOrdered.set(rootIndex, max);
        }
    }


    public Sort(List<Double> unOrdered) {
        this.unOrdered = unOrdered;
    }

    public List<Double> getList() {
        return unOrdered;
    }

    public void setUnOrdered(List<Double> unOrdered) {
        this.unOrdered = unOrdered;
    }
}
