package edu.sust.ex3;

/**
 * Created by SunnyGrocery on 2019/12/31 22:57
 */
public class Bag01 {
    public static void main(String[] args) {
        int totalWeight = 5;//背包容量
        Treasure[] packages = {new Treasure(200, 1),
                new Treasure(100, 3),
                new Treasure(300, 2),
                new Treasure(150, 4),
                new Treasure(350, 5)};
        System.out.println(solution(packages, totalWeight));
    }

    //借用一维数组解决问题 f[w]=max{f[w],f[w-w[i]]+v[i]} 不用装满
    public static int solution(Treasure[] treasures, int totalVolume) {
        int maxValue = -1;
        if (treasures == null || treasures.length == 0 || totalVolume <= 0) {//参数合法性检查
            maxValue = 0;
        } else {
            int trasuresNum = treasures.length;
            int[] f = new int[totalVolume + 1];
            for (int i = 0; i < trasuresNum; i++) {
                int currentVolume = treasures[i].getVolume();
                int currentValue = treasures[i].getValue();
                //注意这里为什么要逆序，前面已经解释过
                for (int j = totalVolume; j >= currentVolume; j--) {
                    f[j] = Math.max(f[j], f[j - currentVolume] + currentValue);
                }
            }
            maxValue = f[totalVolume];
        }
        return maxValue;
    }
}

class Treasure {
    private int value;//价值
    private int volume;//体积

    public Treasure(int value, int volume) {
        this.setValue(value);
        this.setVolume(volume);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}

