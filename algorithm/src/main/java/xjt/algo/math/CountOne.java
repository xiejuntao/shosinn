package xjt.algo.math;

import org.junit.jupiter.api.Test;

/**
 * 给定一个整数，输出这个整数的二进制表示中 1 的个数。例如，给定整数 7，其二进制表示为111，因此输出结果为3。
 * */
public class CountOne {
    /**
     * 可以采用位操作来完成。具体思路如下：
     * 首先，判断这个数的最后一位是否为 1，如果为1，则计数器加1。
     * 然后，通过右移丢弃掉最后一位，循环执行该操作直到这个数等于0为止。
     *
     * */
    public int bitMove(int n){
        int count = 0;
        while (n>0){
            if((n&1)==1){
                count = count + 1;
            }
            n = n>>1;
        }
        return count;
    }
    @Test
    public void test(){
        int n = 117;
        System.out.println(Integer.toString(n,2));
        System.out.println(bitMove(n));
    }
}
