package edu.sust.ex3;

public class NQueens {
    private int[] x;//当前解
    private int N;//皇后个数
    private int sum = 0;//当前已找到的可行方案数

    public int totalNQueens(int n) {
        N = n;
        x = new int[N + 1];
        backTrace(1);
        return sum;
    }

    private boolean place(int col) {
        for (int i = 1; i < col; i++) {
            if (Math.abs(col - i) == Math.abs(x[col] - x[i]) || x[col] == x[i]) {
                return false;
            }
        }
        return true;
    }

    private void backTrace(int t) {
        if (t > N) {
            sum++;
        } else {
            //第t行。遍历全部的节点
            for (int j = 1; j <= N; j++) {
                x[t] = j;
                //假设第j个节点能够放下皇后
                if (place(t)) {
                    //接着放下一个
                    backTrace(t + 1);
                }
            }
        }

    }

    public static void main(String[] args) {
        NQueens n = new NQueens();
        System.out.println(n.totalNQueens(8));
    }
}
