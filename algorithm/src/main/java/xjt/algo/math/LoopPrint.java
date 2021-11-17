package xjt.algo.math;

import org.junit.jupiter.api.Test;

/**
 * 如何不使用循环输出1～100
 * */
public class LoopPrint {
    /**
     * 很多情况下，循环都可以使用递归来给出等价的实现
     * */
    public void print(int n){
        System.out.println(101-n);
        int next = n-1;
        if(0<next&&next<100) {
            print(next);
        }
    }
    @Test
    public void test(){
        print(100);
    }
}
