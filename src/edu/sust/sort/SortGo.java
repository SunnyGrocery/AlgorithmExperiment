package edu.sust.sort;

import java.util.*;

/**
 * Created by SunnyGrocery on 2019/10/16 15:59
 */
public class SortGo {
    private Sort sort;
    private List<Double> unOrdered;

    public static void main(String[] args) {
        int unOrderedSize = 200000;
        SortGo sortGo = new SortGo(unOrderedSize, 100000, 100, false);
        boolean print = false;

        sortGo.goBubble(print);
        sortGo.goQuick(print);

        sortGo.goSelect(print);
        sortGo.goHeap(print);

        sortGo.goInsert(print);
        sortGo.goShell(print, sortGo.getSort().getList().size() / 2);

        sortGo.goMerge(print);
        //外排序，这里用的数据与内排序不同，
        FileMerge fileMerge = new FileMerge("./data/unOrderFile.txt", unOrderedSize, 20000 / 30, 100000, 100, false);
        fileMerge.init();
        fileMerge.fileMergeSort();
    }


    public void goBubble(boolean print) {
        //开始计时
        long startTime = System.currentTimeMillis();

        //排序
        sort.bubbleSort();

        //计时结束
        long usingTime = System.currentTimeMillis() - startTime;

        System.out.println("BubbleSort using time :\t" + usingTime + " ms");
        if (print) {
            System.out.println(sort.getList());
        }
        sort.setUnOrdered(new ArrayList<>(unOrdered));

    }

    public void goSelect(boolean print) {
        //开始计时
        long startTime = System.currentTimeMillis();

        //排序
        sort.selectSort();

        //计时结束
        long usingTime = System.currentTimeMillis() - startTime;

        System.out.println("SelectSort using time :\t" + usingTime + " ms");
        if (print) {
            System.out.println(sort.getList());
        }
        sort.setUnOrdered(new ArrayList<>(unOrdered));
    }

    public void goInsert(boolean print) {
        //开始计时
        long startTime = System.currentTimeMillis();

        //排序
        sort.insertSort();

        //计时结束
        long usingTime = System.currentTimeMillis() - startTime;

        System.out.println("InsertSort using time :\t" + usingTime + " ms");
        if (print) {
            System.out.println(sort.getList());
        }
        sort.setUnOrdered(new ArrayList<>(unOrdered));
    }

    public void goShell(boolean print, int increment) {
        //开始计时
        long startTime = System.currentTimeMillis();

        //排序
        sort.shellSort(increment);

        //计时结束
        long usingTime = System.currentTimeMillis() - startTime;

        System.out.println("ShellSort using time :\t" + usingTime + " ms");
        if (print) {
            System.out.println(sort.getList());
        }
        sort.setUnOrdered(new ArrayList<>(unOrdered));
    }

    public void goQuick(boolean print) {
        //开始计时
        long startTime = System.currentTimeMillis();

        //排序
        sort.quickSort(0, sort.getList().size() - 1);

        //计时结束
        long usingTime = System.currentTimeMillis() - startTime;

        System.out.println("QuickSort using time :\t" + usingTime + " ms");
        if (print) {
            System.out.println(sort.getList());
        }
        sort.setUnOrdered(new ArrayList<>(unOrdered));
    }

    public void goMerge(boolean print) {
        sort.mergeTemp = new ArrayList<>(Arrays.asList(new Double[sort.getList().size()]));
        //开始计时
        long startTime = System.currentTimeMillis();

        //排序
        sort.mergeSort(0, sort.getList().size() - 1);

        //计时结束
        long usingTime = System.currentTimeMillis() - startTime;

        System.out.println("MergeSort using time :\t" + usingTime + " ms");
        if (print) {
            System.out.println(sort.getList());
        }
        sort.setUnOrdered(new ArrayList<>(unOrdered));
    }

    public void goHeap(boolean print) {
        sort.mergeTemp = new ArrayList<>(Arrays.asList(new Double[sort.getList().size()]));
        //开始计时
        long startTime = System.currentTimeMillis();

        //排序
        sort.heapSort();

        //计时结束
        long usingTime = System.currentTimeMillis() - startTime;

        System.out.println("HeapSort using time :\t" + usingTime + " ms");
        if (print) {
            System.out.println(sort.getList());
        }
        sort.setUnOrdered(new ArrayList<>(unOrdered));
    }


    public Sort getSort() {
        return sort;
    }

    public SortGo(int unOrderedSize, int maxLength, int decimal, boolean isRandom) {
        Random random;
        if (isRandom) {
            random = new Random();
        } else {
            random = new Random(47);
        }

        unOrdered = new ArrayList<>();

        for (int i = 0; i < unOrderedSize; i++) {
            unOrdered.add(((double) random.nextInt(maxLength)) / decimal);
        }
//        System.out.println("Before Sort:\n" + unOrdered);
//        for (int i : unOrdered) {
//            System.out.print(i + " ");
//        }
//        System.out.println();
        sort = new Sort(new ArrayList<>(unOrdered));
    }
}
