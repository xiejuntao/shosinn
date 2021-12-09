package xjt.jmm.hb;

import lombok.extern.slf4j.Slf4j;

/**
 * Java内存模型中的Happens-Before原则。https://cloud.tencent.com/developer/article/1734515
 * 【原则一】程序次序规则：在一个线程中，按照代码的顺序，前面的操作Happens-Before于后面的任意操作。
 * 【原则二】volatile变量规则：对一个volatile变量的写操作，Happens-Before于后续对这个变量的读操作。
 * 【原则三】传递规则：如果A Happens-Before B，并且B Happens-Before C，则A Happens-Before C。
 * 【原则四】锁定规则：对一个锁的解锁操作 Happens-Before于后续对这个锁的加锁操作。
 * 【原则五】线程启动规则：线程A启动线程B之后，线程B能够看到线程A在启动线程B之前的操作。
 * 【原则六】线程终结规则：线程A等待线程B完成（在线程A中调用线程B的join()方法实现），当线程B完成后（线程A调用线程B的join()方法返回），则线程A能够访问到线程B对共享变量的操作。
 * 【原则七】线程中断规则：对线程interrupt()方法的调用Happens-Before于被中断线程的代码检测到中断事件(isInterrupted())的发生。
 * 【原则八】对象终结原则
 * */

@Slf4j
public class HappensBeforeExample {
    int x = 0;
    volatile boolean v = false;
    void write(){
        //A
        x = 42;//A happen-before B
        //B
        v = true;
    }
    void read(){
        //C
        if(v){//B happen-before C
            log.info("x={}",x);//A happen-before C
        }
    }
    public static void main(String[] args) {
        HappensBeforeExample happensBeforeExample = new HappensBeforeExample();
        //原则1、2、3、volatile示例
        happensBeforeExample.write();
        happensBeforeExample.read();

        //原则4、锁示例
/*        new Thread(new Runnable() {
            @Override
            public void run() {
                happensBeforeExample.initX();
            }
        },"A").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                happensBeforeExample.initX();
            }
        },"B").start();*/

        //原则5、线程启动规则
        //happensBeforeExample.start();

        //原则6、线程终结规则
        //happensBeforeExample.close();

        //原则7、线程中断规则
        //happensBeforeExample.interrupt();

        //原则8、对象销毁
        //System.gc();
    }

    /**
     * 锁示例
     * 假设变量x的值为0，线程A执行完synchronized代码块之后将x变量的值修改为10，并释放synchronized锁。
     * 当线程B进入synchronized代码块时，能够获取到线程A对x变量的写操作，也就是说，线程B访问到的x变量的值为10。
     * */
    public void initX(){
        synchronized(this){ //自动加锁
            log.info("get lock");
            if(this.x < 10){
                this.x = 10;
                log.info("x={}",x);
            }
            log.info("end");
        } //自动释放锁
        log.info("release lock");
    }
    /**
     * 线程启动规则
     * */
    public void start(){
        Thread newTread = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("x={}",x);
            }
        },"NEW");
        x = 99;
        newTread.start();
        //LockSupport.parkNanos(1);
        x = 77;
        log.info("x={}",x);
    }
    /**
     * 线程终结规则
     * */
    public void close(){
        Thread newThread = new Thread(new Runnable() {
            @Override
            public void run() {
                x = 87;
                log.info("x={}",x);
            }
        });
        newThread.start();;
        try {
            newThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("x={}",x);
    }
    public void interrupt(){
        Thread newThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(Thread.currentThread().isInterrupted()) {
                    log.info("interrupted`x={}", x);
                }
                log.info("x={}",x);
            }
        },"NEW");
        newThread.start();
        x = 101;
        log.info("interrupt`x={}",x);
        newThread.interrupt();
    }
    public HappensBeforeExample(){
        log.info("对象创建");
    }
    @Override
    public void finalize(){
        log.info("对象销毁");
    }
}
