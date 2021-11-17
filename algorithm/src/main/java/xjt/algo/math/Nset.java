package xjt.algo.math;

import org.junit.jupiter.api.Test;
/**
 * 如何求正整数n所有可能的整数组合
 * */
public class Nset {
    public int nSet(int n){
        if(n>0) {
            if (n == 1) {
                return 1;
            } else if(n==2){
                return 3;//11、20
            } else{
                    return 1 + nSet(n - 1);
            }
        }else{
            return 0;
        }
    }
    @Test
    public void test(){
        System.out.println(nSet(17));
    }
}
