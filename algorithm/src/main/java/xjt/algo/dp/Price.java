package xjt.algo.dp;
/**
 * 动态规划：拼单
 * 淘宝的“双十一”购物节有各种促销活动，比如“满 200 元减 50 元”。
 * 假设你女朋友的购物车中有 n 个（n>100）想买的商品，她希望从里面选几个，
 * 在凑够满减条件的前提下，让选出来的商品价格总和最大程度地接近满减条件（200 元），这样就可以极大限度地“薅羊毛”。
 * */
public class Price {
    /**
     * @param items 商品价格
     * @param n 商品数量
     * @param w 满减条件，比如200
     * */
    public void doubleElven(int[] items,int n,int w){
        int ww = 3*w;
        boolean[][] states = new boolean[n][ww + 1];//总价超过3倍就没有“薅羊毛”的价值
        states[0][0] = true;
        if(items[0] <= ww){
            states[0][items[0]] = true;
        }
        for(int i=1;i<n;i++){
            for(int j=0;j<ww;j++){
                if(states[i-1][j]==true){
                    states[i][j] = states[i-1][j];//不买
                    int v = j + items[i];
                    if(v<=ww){
                        states[i][v] = true;
                    }
                }
            }
        }

        int j;
        for(j=w;j<ww+1;j++){
            if(states[n-1][j]==true){
                break;
            }
        }
        if(j==ww+1){
            throw new IllegalStateException("没有可行解");
        }
        for(int i=n-1;i>=1;i--){
            if(j-items[i]>=0&&states[i-1][j-items[i]]==true){
                System.out.print(items[i] + "  ");
                j = j-items[i];
            }
        }
        if(j!=0){
            System.out.print(items[0]);
        }
    }

    public static void main(String[] args) {
        Price price = new Price();
        int[] items = {299,199,199,67,64,57,201,2,500,54,99,360,168};
        int n = 3;
        int w = 500;
        price.doubleElven(items,n,w);
    }
}
