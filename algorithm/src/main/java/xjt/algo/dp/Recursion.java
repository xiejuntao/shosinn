package xjt.algo.dp;
/**
 * 动态规划/递推=递归+缓存子结果
 * 斐波那契数列：0,1,1,2,3,5,8,13
 * */
public class Recursion {
    public int f1(int n){
        if(n==0){
            return 0;
        }else if(n==1){
            return 1;
        }else{
            return f1(n-1)+f1(n-2);
        }
    }
    public int f2(int n){
        return n<=1?n:f2(n-1)+f2(n-2);
    }
    public int f3(int n,int[] cache){
        if(n<=1){
            return n;
        }else{
            if(cache[n]<=0){
                cache[n] = f3(n-1,cache)+f3(n-2,cache);
            }
            return cache[n];
        }
    }
    public int f4(int n){
        int[] states = new int[100];
        states[0]=0;
        states[1]=1;
        int i=2;
        for(;i<=n;i++){
           states[i] = states[i-1]+states[i-2];
        }
        return states[n];
    }
    public static void main(String[] args) {
        Recursion recursion = new Recursion();
/*        System.out.println(recursion.f1(7));
        System.out.println(recursion.f2(7));
        System.out.println(recursion.f3(7,new int[8]));*/
        System.out.println(recursion.f4(7));
    }
}
