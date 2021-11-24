package xjt.algo.backtracking;
/**
 * 0-1 背包
 * 有一个背包，背包总的承载重量是 Wkg。现在我们有 n 个物品，每个物品的重量不等，并且不可分割。
 * 我们现在期望选择几件物品，装载到背包中。在不超过背包所能装载重量的前提下，如何让背包中物品的总重量最大？
 * */
public class ZeroOneBag {
    public int maxW = Integer.MIN_VALUE;//存储背包中物品总重量的最大值
    /**
     * 假设背包可承受重量100，物品个数10，物品重量存储在数据a中，可以这样调用函数：bag(0,0,a,10,100)
     * @param i 考察到哪个物品了，下标从0开始
     * @param cw 当前已经装进去的物品的重量和
     * @param items 每个物品的重量
     * @param n 表示每个物品的重量
     * @param w 背包承载重量
     * */
    public void bag(int i,int cw,int[] items,int n,int w){
        if(cw==w||i==n){//cw==w表示装满了，i==n表示已经考察完所有的物品
            if(cw>maxW){
                maxW=cw;
            }
            return;
        }
        bag(i+1,cw,items,n,w);//选择不装第i个物品，但是还要继续递归至下下一个物品(下标从0开始)
        if(cw+items[i]<=w){
            bag(i+1,cw+items[i],items,n,w);// 选择装第i个物品
        }
    }
}
