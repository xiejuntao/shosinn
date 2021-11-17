package xjt.algo.math;
/**
 *  如何判断一个数是否为2的n次方
 *
 * */
public class Quadratic {
    public static void main(String[] args) {
        System.out.println(isPower(3));
        System.out.println(isPower(16));
        System.out.println(isPower(64616));
    }
    /**
     * 通过对2^0，2^1，2^2，…，2^n进行分析，发现这些数字的二进制形式分别为：1，10，100，…。从二进制的表示可以看出，
     * 如果一个数是2的n次方，那么这个数对应的二进制表示中有且只有一位是1，其余位都为0。
     * 因此，判断一个数是否为2的n次方可以转换为这个数对应的二进制表示中是否只有一位为1。
     * 如果一个数的二进制表示中只有一位是1，例如num=00010000，那么num-1的二进制表示为num-1=00001111。
     * 由于num与num-1二进制表示中每一位都不相同，因此num&（num-1）的运算结果为0。
     * */
    public static boolean isPower(int n){
        if((n&(n-1))==0){
            return true;
        }else{
            return false;
        }
    }
}
