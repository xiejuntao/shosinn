package xjt.id.snowflake;

import java.util.Date;

/**
 * A snowflake is a source of k-ordered unique 64-bit integers.
 */
public class Snowflake {

    public static final int NODE_SHIFT = 10;
    public static final int SEQ_SHIFT = 12;

    public static final short MAX_NODE = 1024;
    public static final short MAX_SEQUENCE = 4096;

    private short sequence;
    private long referenceTime;

    private int node;

    /**
     * A snowflake is designed to operate as a singleton instance within the context of a node.
     * If you deploy different nodes, supplying a unique node id will guarantee the uniqueness
     * of ids generated concurrently on different nodes.
     *
     * @param node This is an id you use to differentiate different nodes.
     */
    public Snowflake(int node) {
        if (node < 0 || node > MAX_NODE) {
            throw new IllegalArgumentException(String.format("node must be between %s and %s", 0, MAX_NODE));
        }
        this.node = node;
    }

    /**
     * Generates a k-ordered unique 64-bit integer. Subsequent invocations of this method will produce
     * increasing integer values.
     *
     * @return The next 64-bit integer.
     *
     * 整体长度通常是 64 （1 + 41 + 10+ 12 = 64）位，适合使用 Java 语言中的 long 类型来存储。
     * 头部是 1 位的正负标识位。
     * 紧跟着的高位部分包含 41 位时间戳，通常使用 System.currentTimeMillis()。
     * 后面是 10 位的 WorkerID，标准定义是 5 位数据中心 + 5 位机器 ID，组成了机器编号，以区分不同的集群节点。
     * 最后的 12 位就是单位毫秒内可生成的序列号数目的理论极限。
     */
    public long next() {

        long currentTime = System.currentTimeMillis();
        long counter;

        synchronized(this) {

            if (currentTime < referenceTime) {
                throw new RuntimeException(String.format("Last referenceTime %s is after reference time %s", referenceTime, currentTime));
            } else if (currentTime > referenceTime) {
                this.sequence = 0;
            } else {
                if (this.sequence < Snowflake.MAX_SEQUENCE) {
                    this.sequence++;
                } else {
                    throw new RuntimeException("Sequence exhausted at " + this.sequence);
                }
            }
            counter = this.sequence;
            referenceTime = currentTime;
        }

        return currentTime << NODE_SHIFT << SEQ_SHIFT | node << SEQ_SHIFT | counter;
    }

    public static void main(String[] args) {
        //gen();
        long t = 1l<<41;
        System.out.println(t);
        System.out.println(new Date(t));//2039-1970=69
    }
    public static void gen(){
        long t = System.currentTimeMillis();//41位精确度毫秒
        int NODE_SHIFT = 10;//10位节点数，最大1024
        int SEQ_SHIFT = 12;//12位序列数，最大4096
        int counter = 227;
        int node = 117;
        long id = t<<NODE_SHIFT<<SEQ_SHIFT | node<<SEQ_SHIFT | counter;//1位符号位
        System.out.println(id);
        System.out.println(id & ((1 << SEQ_SHIFT)-1));//counter
        long nc = id & ((1 << NODE_SHIFT<< SEQ_SHIFT)-1);//node+counter
        System.out.println(nc>>SEQ_SHIFT);//node=右移去掉counter
    }
    public static void test(){
        long t = System.currentTimeMillis();
        //System.out.println(Long.toBinaryString(t));
        //System.out.println(Long.toBinaryString(t).length());//41位
        //System.out.println(t);
        //System.out.println(Long.toBinaryString(t << 1));
        //System.out.println(Long.toBinaryString(t << 2));
        /**
         * 1位符号位，41位毫秒时间戳，10位节点，12位毫秒序列数=64位
         * */
        int NODE_SHIFT = 10;
        int SEQ_SHIFT = 12;
        int counter = 27;
        // System.out.println(Long.toBinaryString(1 << 10));
        //System.out.println(Long.toBinaryString(1 << 12));
        //System.out.println(1<<10);
        //System.out.println(1<<12);
        //System.out.println(Long.toBinaryString(t<<NODE_SHIFT<<SEQ_SHIFT));
        //System.out.println(Long.toBinaryString(t<<NODE_SHIFT<<SEQ_SHIFT).length());//63

        int node = 17;
        long id = t<<NODE_SHIFT<<SEQ_SHIFT|node<<SEQ_SHIFT|counter;


        //System.out.println(Long.toBinaryString(t<<NODE_SHIFT<<SEQ_SHIFT|node<<SEQ_SHIFT));

        System.out.println(Long.toBinaryString(id));
        int cs = (1 << SEQ_SHIFT)-1;
        //System.out.println(Long.toBinaryString(node));
        //System.out.println(Long.toBinaryString(node<<SEQ_SHIFT));
        //System.out.println(Long.toBinaryString(counter));

        System.out.println(Long.toBinaryString(id & cs));
        System.out.println(id&cs);//counter

        long tl = (1<<NODE_SHIFT<<SEQ_SHIFT)-1;
        System.out.println(Long.toBinaryString(tl));
        //System.out.println(Long.toBinaryString(tl).length());

        System.out.println(Long.toBinaryString(1<<SEQ_SHIFT));


        long ns = id & ((1 << NODE_SHIFT<< SEQ_SHIFT)-1);
        //System.out.println(ns);
        System.out.println(Long.toBinaryString(ns));
        System.out.println(ns>>SEQ_SHIFT);
    }
}
