package xjt.algo.recursion;

import java.util.HashMap;

/**
 * 递归：去的过程叫“递”，回来的过程叫“归”。
 * 1、一个问题的解可以分解为几个子问题的解。
 * 2、这个问题与分解之后的子问题，除了数据规模不同，求解思路完全一样。
 * 3、存在递归终止条件。
 * ==》写出递推公式，找到终止条件。
 * */
public class TestRecursion {
    public static int seats(int n){
        if(n<=0){
            throw new IllegalArgumentException("n must greater than 0");
        }
        if(n==1){
            return 1;
        }else{
            return seats(n-1)+1;
        }
    }
    public static HashMap<Integer,Integer> stepsMap = new HashMap<>();
    /**
     *假如这里有 n 个台阶，每次你可以跨 1 个台阶或者 2 个台阶，请问走这 n 个台阶有多少种走法？
     * */
    public static int steps(int n){
        if(n<=0){
            throw new IllegalArgumentException("n must greater than 0");
        }
        int result = -1;
        if(n==1){
            result = 1;
        }else if(n==2){
            result = 2;
        }else {
            if(stepsMap.containsKey(n)){//避免重复计算
                result = stepsMap.get(n);
            }else {
                result = steps(n - 1) + steps(n - 2);
                stepsMap.put(n,result);
            }
        }
        System.out.println("steps("+n+") = " + result);
        return result;
    }
    public static void main(String[] args) {
        //System.out.println(seats(29));
        System.out.println(steps(11));
    }
}
