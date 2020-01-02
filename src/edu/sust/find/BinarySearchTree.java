package edu.sust.find;

/**
 * Created by SunnyGrocery on 2019/12/31 18:25
 */

public class BinarySearchTree {
    private Node root;

    public void insert(int data) {
        Node newNode = new Node();
        newNode.val = data;
        if (root == null) {
            //如果是第一个节点，也就是根节点为null,直接创建一个新的节点即可　
            root = newNode;
        } else {
            Node current = root;
            //current节点的父节点
            Node parent;
            //循环查找插入的位置
            while (true) {
                parent = current;
                //如果插入的值小于当前节点的值，从左子树查找
                if (data < current.val) {
                    current = current.leftChild;
                    //直到当前节点为null
                    if (current == null) {
                        //设置当前节点的父节点的左子节点为新创建的节点
                        parent.leftChild = newNode;
                        return;
                    }

                }
                //如果插入的值大于当前节点的值，从左子树查找
                else {
                    current = current.rightChild;
                    //直到当前节点为null
                    if (current == null) {
                        //设置当前节点的父节点的右子节点为新创建的节点
                        parent.rightChild = newNode;
                        return;
                    }
                }
            }// end of while(true)
        }
    }

    public Node find(int value) {

        Node current = root;

        while (current.val != value) {

            if (value < current.val) {
                current = current.leftChild;
            } else {
                current = current.rightChild;
            }
            if (current == null) {
                return null;
            }
        }

        return current;
    }
}

class Node {
    //节点值
    int val;
    //左子节点引用
    Node leftChild;
    //右子节点引用
    Node rightChild;

    public void printNode() {
        System.out.println(val);
    }
}
