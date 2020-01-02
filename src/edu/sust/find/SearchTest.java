package edu.sust.find;

import java.awt.geom.Area;
import java.util.*;

/**
 * Created by SunnyGrocery on 2019/11/27 16:11
 */
public class SearchTest {
    public static void main(String[] args) {
        for (Search s : searches) {
            int index = s.doSearch();
            System.out.println("    + index:" + index);
        }
    }

    private static int[] orderArray;

    static {
        int SIZE = 200000;
        orderArray = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            orderArray[i] = i;
        }
    }

    public static Search[] searches = new Search[]{
            new Search(orderArray, "Order Search") {
                @Override
                public int search(int lookFor) {
                    for (int i = 0; i < orderArray.length; i++) {
                        if (orderArray[i] == lookFor) {
                            return i;
                        }
                    }
                    return -1;
                }
            },
            new Search(orderArray, "Binary Search") {
                @Override
                public int search(int lookFor) {
                    return Arrays.binarySearch(orderArray, lookFor);
                }
            },
            new Search(orderArray, "Binary Search Tree") {
                @Override
                public int search(int lookFor) {
                    BinarySearchTree bst = new BinarySearchTree();
                    for (int i : orderArray) {
                        bst.insert(i);
                    }
                    return bst.find(lookFor).val;
                }
            },

    };

    public abstract static class Search {
        protected int[] toFind;
        protected String name;
        private static int random = new Random().nextInt(1000);

        public int doSearch() {
            System.out.print(name);
            long start = System.currentTimeMillis();
            int index = search(random);
            long duration = System.currentTimeMillis() - start;
            System.out.println(":" + duration + " ms");

            return index;
        }

        public Search(int[] array, String name) {
            this.toFind = array;
            this.name = name;
        }

        public abstract int search(int lookFor);

    }
}
