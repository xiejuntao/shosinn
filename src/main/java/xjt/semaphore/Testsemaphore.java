package xjt.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class Testsemaphore {
    public static void main(String[] args) {
        //Semaphore semaphore = new Semaphore(2);
       log("start");
        //LockSupport.parkUntil(System.currentTimeMillis()+5000);
        LockSupport.parkNanos(2*1000*1000*1000);
        System.out.println(TimeUnit.SECONDS.toNanos(3));
        System.out.println(3*1000*1000*1000L);
        System.out.println(Integer.MAX_VALUE);
        log("end");
    }
    public static void log(String l){
        System.out.println("["+System.currentTimeMillis()+"] "+l);
    }
}
