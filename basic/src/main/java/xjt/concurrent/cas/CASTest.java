package xjt.concurrent.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class CASTest implements Runnable{
    private static AtomicBoolean flag = new AtomicBoolean(true);
    @Override
    public void run() {
        printInfo();
        if (flag.compareAndSet(true, false)) {
            printInfo();
            sleepFiveSeconds();
            flag.set(true);
        }else {
            printInfo();
            sleepFiveSeconds();
            run();
        }
    }
    public void printInfo(){
        System.out.println("["+System.currentTimeMillis()+"]thread:"+Thread.currentThread().getName()+";flag:"+flag.get());
    }
    public void sleepFiveSeconds(){
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        CASTest casTest = new CASTest();
        Thread thread0 = new Thread(casTest,"thread0");
        Thread thread1 = new Thread(casTest,"thread1");
        thread0.start();
        thread1.start();
    }
}
