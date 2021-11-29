package xjt.algo.dp;
/**
 * 动态规划：0-1 背包问题
 * 1、把问题分解为多个阶段，每个阶段对应一个决策。
 * 2、我们记录每一个阶段可达的状态集合（去掉重复的），然后通过当前阶段的状态集合
 * ，来推导下一个阶段的状态集合，动态地往前推进。
 * */
public class Knapsack {
    /**
     * 对于一组不同重量、不可分割的物品，我们需要选择一些装入背包，
     * 在满足背包最大重量限制的前提下，背包中物品总重量的最大值是多少呢？
     * @param weight 物品重量
     * @param n 物品个数
     * @param w 背包可承载重量
     * */
    public int knapsack(int[] weight,int n,int w){
        boolean[][] states = new boolean[n][w+1];
        states[0][0] = true;//
        int first = weight[0];
        if(first<=w){
            states[0][first]=true;//第一行数据特殊处理，可以利用哨兵优化。
        }
        for(int i=1;i<n;++i){//动态规划状态转移
            for(int j=0;j<=w;++j){
                if(states[i-1][j]==true){
                    states[i][j] = true;//不把第i个物品放入背包
                    int getWeight = j+weight[i];//把第i个物品放入背包
                    if(getWeight<=w){
                        states[i][getWeight]=true;
                    }
                }
            }
        }
        //输出结果
        //我们只需要在最后一层(n-1)，找一个值为 true 的最接近 w的值，就是背包中物品总重量的最大值。
        for(int i=w;i>=0;--i){
            if(states[n-1][i]==true){
                return i;
            }
        }
        return 0;
    }
    /**
     * 对于一组不同重量、不可分割的物品，我们需要选择一些装入背包，
     * 在满足背包最大重量限制的前提下，背包中物品总重量的最大值是多少呢？
     * @param weight 物品重量
     * @param n 物品个数
     * @param w 背包可承载重量
     * */
    public int knapsackV2(int[] weight,int n,int w){
        boolean[] states = new boolean[w+1];//
        states[0] = true;
        if(weight[0]<=w){
            states[weight[0]] = true;
        }
        for(int i=1;i<n;++i){
            for(int j= w-weight[i];j>=0;--j){
                if(states[j]==true){
                    states[j+weight[i]] = true;
                }
            }
        }
        for (int i = w; i >= 0; --i) {
            // 输出结果
            if (states[i] == true) return i;
        }
        return 0;
    }
    /**
     * 对于一组不同重量、不同价值、不可分割的物品，我们选择将某些物品装入背包，
     * 在满足背包最大重量限制的前提下，背包中可装入物品的总价值最大是多少呢？
     * 1、用一个二维数组 states[n][w+1]，来记录每层可以达到的不同状态。（当前状态对应的最大总价值）
     * 2、每一层中 (i, cw) 重复的状态（节点）合并，只记录 cv 值最大的那个状态，然后基于这些状态来推导下一层的状态。
     * @param weight 物品重量
     * @param value 物品价值
     * @param n 物品数量
     * @param w 背包容量
     * */
    public int knapsackV3(int[] weight,int[] value,int n,int w){
        int[][] states = new int[n][w+1];
        for(int i=0;i<n;i++){
            for(int j=0;j<w+1;j++){
                states[i][j]=-1;
            }
        }
        states[0][0]=0;
        int first = weight[0];
        if(first<=w){
            states[0][first] = value[0];
        }
        for(int i=1;i<n;i++){
            for(int j=0;j<=w;j++){
                if(states[i-1][j]>-1){
                    states[i][j] = states[i-1][j];//不放入背包
                }
                int getWeight = j+weight[i];//放入背包
                if(getWeight<=w){
                    int v = states[i-1][j] + value[i];
                    //if(v > states[i][getWeight])//TODO 合并？
                    states[i][getWeight] = v;
                }
            }
        }
        int maxValue = -1;
         for (int j = 0; j <= w; ++j) {
             if (states[n - 1][j] > maxValue) {
                 maxValue = states[n - 1][j];
             }
         }
         return maxValue;

    }
    public static void main(String[] args) {
        int[] weight = new int[]{2,3,5,4,1,6};
        int[] value = new int[]{21,1,17,7,6,9};
        int w = 6;
        Knapsack knapsack = new Knapsack();
        //System.out.println(knapsack.knapsack(weight,weight.length,w));
       // System.out.println(knapsack.knapsackV2(weight,weight.length,w));
        System.out.println(knapsack.knapsackV3(weight,value,weight.length,w));//结果不对
    }
}
