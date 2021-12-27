package xjt.algo.util.bitmap;
/**
 * 位存储
 * @see java.util.BitSet
 * */
public class BitMap {
    private char[] bytes;
    private int nbits;
    public BitMap(int nbits){
        this.nbits = nbits;
        this.bytes = new char[nbits/16 + 1];//char类型2个字节16位
    }
    public void set(int k){
        if(k>nbits){
            throw new IllegalArgumentException("param too long");
        }
        int byteIndex = k/16;
        int bitIndex = k%16;
        bytes[byteIndex] |= (1<<bitIndex);
    }
    public boolean get(int k){
        if(k>nbits){
            return false;
        }
        int byteIndex = k/16;
        int bitIndex = k%16;
        return (bytes[byteIndex]&(1<<bitIndex)) !=0;
    }
    public static void main(String[] args) {

        System.out.println(Integer.toString(1<<3,2));
    }
}
