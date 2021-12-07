package xjt.ref;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * https://www.cnblogs.com/CodeBear/p/12447554.html
 * */
public class TestRef {
    public static void main(String[] args) {//-Xmx20M
        //softRef();
        weakRef();
        //phantomRef();
    }
    /**
     * 虚引用又被称为幻影引用
     * 1、无法通过虚引用来获取对一个对象的真实引用。
     * 2、虚引用必须与ReferenceQueue一起使用，当GC准备回收一个对象，如果发现它还有虚引用，就会在回收之前，把这个虚引用加入到与之关联的ReferenceQueue中。
     * 虚引用有什么用呢？在NIO中，就运用了虚引用管理堆外内存。
     * */
    public static void phantomRef(){
//        ReferenceQueue queue = new ReferenceQueue();
//        PhantomReference<byte[]> reference = new PhantomReference<byte[]>(new byte[1], queue);
//        System.out.println(reference.get());
        ReferenceQueue queue = new ReferenceQueue();
        List<byte[]> bytes = new ArrayList<>();
        PhantomReference<Life> reference = new PhantomReference<Life>(new Life(),queue);
        new Thread(() -> {
            for (int i = 0; i < 100;i++ ) {
                bytes.add(new byte[1024 * 1024]);
            }
        }).start();

        new Thread(() -> {
            while (true) {
                Reference poll = queue.poll();
                if (poll != null) {
                    System.out.println("虚引用被回收了：" + poll);
                }
            }
        }).start();
        //阻塞线程
        Scanner scanner = new Scanner(System.in);
        scanner.hasNext();
    }
    /**
     * 弱引用
     * 1、弱引用的特点是不管内存是否足够，只要发生GC，都会被回收
     * 2、回收不触发finalize()
     * 弱引用在很多地方都有用到，比如ThreadLocal、WeakHashMap。
     * */
    public static void weakRef(){
        WeakReference<byte[]> weakReference = new WeakReference<byte[]>(new byte[1]);
        System.out.println(weakReference.get());
        System.gc();
        System.out.println(weakReference.get());
    }
    /**
     * 软引用
     * 1、只有在内存不足，JVM才会回收该对象。(显示gc不会回收对象)
     * 2、回收不触发finalize()
     * 软引用到底有什么用呢？比较适合用作缓存，当内存足够，可以正常的拿到缓存，当内存不够，就会先干掉缓存，不至于马上抛出OOM。
     * */
    public static void softRef(){
        SoftReference<byte[]> softReference = new SoftReference<byte[]>(new byte[1024*1024*10]);
        System.out.println(softReference.get());
        System.gc();
        System.out.println(softReference.get());//-Xmx20M

        byte[] bytes = new byte[1024 * 1024 * 10];
        System.out.println(softReference.get());
    }
    /**
     * 强引用
     * 只要某个对象有强引用与之关联，这个对象永远不会被回收，即使内存不足，JVM宁愿抛出OOM，也不会去回收。
     * */
    public static void strongRef(){
        Life life = new Life();//强引用是最普遍的一种引用，我们写的代码，99.9999%都是强引用：
        life = null;
        System.gc();
    }
}
