package xjt.algo.util.bitmap;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.jupiter.api.Assertions;

import java.util.HashSet;
import java.util.Set;

public class BF {
    public final static int DATA_SIZE = 100*10000;
    //-Xms32m -Xmx32m -XX:+PrintHeapAtGC -XX:+HeapDumpOnOutOfMemoryError
    public static void main(String[] args) {
        //mockHash();
        //testSimpleBloomFilter();
        testRealBloomFilter();
    }
    public static void testRealBloomFilter(){
        long star = System.currentTimeMillis();
        BloomFilter bloomFilter = BloomFilter.create(Funnels.integerFunnel(),DATA_SIZE);
        for (int i = 0; i < DATA_SIZE; i++) {
            bloomFilter.put(i);
        }
        System.out.println(bloomFilter.mightContain(1));
        System.out.println(bloomFilter.mightContain(2));
        System.out.println(bloomFilter.mightContain(-753469431));
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - star));
    }
    public static void testSimpleBloomFilter(){
        long star = System.currentTimeMillis();
        SimpleBloomFilter simpleBloomFilter = new SimpleBloomFilter(DATA_SIZE/10);
        for (int i = 0; i < DATA_SIZE; i++) {
            simpleBloomFilter.add(i+"") ;
        }
        System.out.println(simpleBloomFilter.contains(1));
        System.out.println(simpleBloomFilter.contains(2));
        System.out.println(simpleBloomFilter.contains(-753469431));
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - star));
    }
    /**
     *使用 K 个哈希函数，对同一个数字进行求哈希值，那会得到 K 个不同的哈希值，我们分别记作 X1​，X2​，X3​，…，XK​。
     * 我们把这 K 个数字作为位图中的下标，将对应的 BitMap[X1​]，BitMap[X2​]，BitMap[X3​]，…，BitMap[XK​]都设置成 true，
     * 也就是说，我们用 K 个二进制位，来表示一个数字的存在。
     * */
    private static class SimpleBloomFilter{
        /**
         * 数组长度
         */
        private int arraySize;
        /**
         * 数组
         */
        private int[] array;
        public SimpleBloomFilter(int arraySize) {
            this.arraySize = arraySize;
            array = new int[arraySize];
        }
        public void add(int data){
            add(data+"");
        }
        public boolean contains(int data){
            return check(data+"");
        }
        /**
         * 写入数据
         * @param key
         */
        public void add(String key) {
            int first = hashcode_1(key);
            int second = hashcode_2(key);
            int third = hashcode_3(key);
            array[first % arraySize] = 1;
            array[second % arraySize] = 1;
            array[third % arraySize] = 1;
        }
        /**
         * 判断数据是否存在
         * @param key
         * @return
         */
        public boolean check(String key) {
            int first = hashcode_1(key);
            int second = hashcode_2(key);
            int third = hashcode_3(key);
            int firstIndex = array[first % arraySize];
            if (firstIndex == 0) {
                return false;
            }
            int secondIndex = array[second % arraySize];
            if (secondIndex == 0) {
                return false;
            }
            int thirdIndex = array[third % arraySize];
            if (thirdIndex == 0) {
                return false;
            }
            return true;
        }
        /**
         * hash 算法1
         * @param key
         * @return
         */
        private int hashcode_1(String key) {
            int hash = 0;
            int i;
            for (i = 0; i < key.length(); ++i) {
                hash = 33 * hash + key.charAt(i);
            }
            return Math.abs(hash);
        }
        /**
         * hash 算法2
         * @param data
         * @return
         */
        private int hashcode_2(String data) {
            final int p = 16777619;
            int hash = (int) 2166136261L;
            for (int i = 0; i < data.length(); i++) {
                hash = (hash ^ data.charAt(i)) * p;
            }
            hash += hash << 13;
            hash ^= hash >> 7;
            hash += hash << 3;
            hash ^= hash >> 17;
            hash += hash << 5;
            return Math.abs(hash);
        }
        /**
         *  hash 算法3
         * @param key
         * @return
         */
        private int hashcode_3(String key) {
            int hash, i;
            for (hash = 0, i = 0; i < key.length(); ++i) {
                hash += key.charAt(i);
                hash += (hash << 10);
                hash ^= (hash >> 6);
            }
            hash += (hash << 3);
            hash ^= (hash >> 11);
            hash += (hash << 15);
            return Math.abs(hash);
        }
    }
    public  static void mockHash(){
        long star = System.currentTimeMillis();
        Set<Integer> hashset = new HashSet<>(DATA_SIZE) ;
        for (int i = 0; i < DATA_SIZE; i++) {
            hashset.add(i) ;
        }
        Assertions.assertTrue(hashset.contains(1));
        Assertions.assertTrue(hashset.contains(2));
        Assertions.assertTrue(hashset.contains(3));
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - star));
    }
    public static void testGuavaBloomFilter(){
        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8),10000,0.001);
        bloomFilter.put("念");
        bloomFilter.put("天");
        bloomFilter.put("地");
        bloomFilter.put("之");
        bloomFilter.put("悠");
        bloomFilter.put("悠");
        bloomFilter.put("独");

        System.out.println(bloomFilter.mightContain("念"));
        System.out.println(bloomFilter.mightContain("而"));
    }
}
