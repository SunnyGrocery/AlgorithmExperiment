package edu.sust.sort;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by SunnyGrocery on 2019/10/24 9:27
 */
public class FileMerge {
    private String PATH;
    //数的个数
    private int unOrderSize;
    //一个数的总长度
    private int maxLength;
    //一个数的小数点位数
    private int decimal;
    //是否以时间戳作为随机种子
    private boolean isRandom;
    //切分粒度
    private int granularity;
    //生成的待排有序文件
    private File unOrderFile;
    //切分的待排文件数
    private int smallFileNum;
    //中转文件
    private File tempFile;
    //这里从切分排序文件开始计时
    private long startTime;

    public static void main(String[] args) {
        FileMerge fileMerge = new FileMerge("./data/unOrderFile.txt", 200000, 10000, 1000000, 10, true);
        //生成随机文件并切分，切分时内排序（内排序策略为快速排序）
        fileMerge.init();
        //归并外排序
        fileMerge.fileMergeSort();
    }

    //归并策略是将两个文件中后一个文件归并到后一个文件中，最后排好序的文件存放在第一个文件中
    public void fileMergeSort() {
        System.out.println("++Start merge sort...");
        //j(各层的跨度i的跨度)每次乘2
        for (int j = 1; j < smallFileNum; j *= 2) {
            //i（跨度）每次向后移动j*2
            for (int i = 0; i + j < smallFileNum; i = i + j * 2) {
                merge(i, i + j);
                System.out.println("> Merged " + i + " and " + (i + j) + " to " + i);
            }
            if (j * 2 < smallFileNum) {
                System.out.println("- Next storey...");
            }
        }

        System.out.println("# End merge sort!");
        long usingTime = System.currentTimeMillis() - startTime;
        System.out.println("- Stop timing...");
        System.out.println("Use time : " + usingTime + " ms");
        System.out.println("External sorting using time :\t" + usingTime + " ms");
        System.out.println("[./data/t_0.txt] is sort result :)");
    }

    public void merge(int fileIndex1, int fileIndex2) {
        File file1 = new File(getFilePathByFileIndex(fileIndex1));
        File file2 = new File(getFilePathByFileIndex(fileIndex2));
        try {
            Scanner scanner1 = new Scanner(new FileReader(file1));
            Scanner scanner2 = new Scanner(new FileReader(file2));
            FileOutputStream bufferFileOutputStream = new FileOutputStream(tempFile);
            //每个文件逻辑上至少有一个double，故这里初始化可以不做检查
            double a = scanner1.nextDouble();
            double b = scanner2.nextDouble();
            while (true) {
                if (a < b) {
                    bufferFileOutputStream.write((a + " ").getBytes());
                    if (scanner1.hasNextDouble()) {
                        a = scanner1.nextDouble();
                    } else {
                        bufferFileOutputStream.write((b + " ").getBytes());
                        break;
                    }

                } else {
                    bufferFileOutputStream.write((b + " ").getBytes());
                    if (scanner2.hasNextDouble()) {
                        b = scanner2.nextDouble();
                    } else {
                        bufferFileOutputStream.write((a + " ").getBytes());
                        break;
                    }
                }
            }
            while (scanner1.hasNextDouble()) {
                bufferFileOutputStream.write((scanner1.nextDouble() + " ").getBytes());
            }
            while (scanner2.hasNextDouble()) {
                bufferFileOutputStream.write((scanner2.nextDouble() + " ").getBytes());
            }
            //copy回file1
            FileInputStream in = new FileInputStream(tempFile);
            FileOutputStream out = new FileOutputStream(file1);
            //从in中批量读取字节，放入到buf这个字节数组中，
            // 从第0个位置开始放，最多放buf.length个 返回的是读到的字节的个数
            byte[] buf = new byte[8 * 1024];
            int len = 0;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
                out.flush();
            }
            scanner1.close();
            scanner2.close();
            bufferFileOutputStream.close();
            in.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("Merge failed");
        }
    }

    public void init() {
        System.out.println("++ Init start!");
        System.out.println("> Creating random order file...");
        unOrderFile = createFile(PATH);
        tempFile = new File(unOrderFile.getParent() + "/temp.txt");
        Random random;
        if (isRandom) {
            random = new Random();
        } else {
            random = new Random(47);
        }
        //填充待排数据
        if (unOrderFile.canWrite()) {
            try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(unOrderFile))) {
                for (int i = 0; i < unOrderSize; i++) {
                    double temp = ((double) random.nextInt(maxLength)) / decimal;
                    fileWriter.write(temp + " ");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("Can`t write file");
        }

        System.out.println("> Create file operation success!");
        System.out.println("> Segregating file...");
        startTime = System.currentTimeMillis();
        System.out.println("+ Start timing...");
        segregateFile();
        System.out.println("- Init end!");
    }

    private ArrayList<Double> sortTempList = new ArrayList<>();

    public void segregateFile() {
        try {
            Scanner scanner = new Scanner(unOrderFile);
            smallFileNum = 0;

            for (int i = 0; i < unOrderSize; i += granularity, smallFileNum++) {
                File file = createFile(getFilePathByFileIndex(smallFileNum));
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                //从原文件中读取
                while (scanner.hasNextDouble() && sortTempList.size() < granularity) {
                    sortTempList.add(scanner.nextDouble());
                }
                //给每个文件要排序,由于目标是实现归并外排序，故这里我认为可以调用其他排序算法，没有必要一定用归并进行内排序
                sortTempList.sort((a, b) -> {
                    if (Math.abs(a - b) < 1e-6) {
                        return 0;
                    } else if (a - b > 0) {
                        return 1;
                    } else {
                        return -1;
                    }
                });
                for (double d : sortTempList) {
                    fileOutputStream.write((d + " ").getBytes());
                }
                sortTempList.clear();
                System.out.println("> " + file.getName() + " generated");
            }
            System.out.println("> A total of " + smallFileNum + " files were generated");
        } catch (IOException e) {
            throw new RuntimeException("Segregate failed");
        }
        System.out.println("> Segregate operation success!");
    }

    public File createFile(final String PATH) {
        File file = new File(PATH);
        File dir = file.getParentFile();
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new RuntimeException("Create dir failed!");
            }
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Create file failed!");
            }
        }
        return file;
    }

    public String getFilePathByFileIndex(int fileIndex) {
        return unOrderFile.getParent() + "/t_" + fileIndex + ".txt";
    }


    public FileMerge(String PATH, int unOrderSize, int granularity, int maxLength, int decimal, boolean isRandom) {
        this.PATH = PATH;
        this.unOrderSize = unOrderSize;
        this.maxLength = maxLength;
        this.decimal = decimal;
        this.isRandom = isRandom;
        this.granularity = granularity;
    }


}
