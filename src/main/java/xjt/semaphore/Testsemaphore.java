package xjt.semaphore;

import xjt.utils.SUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class Testsemaphore {
    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(2);
        //semaphore.acquire();
        ExecutorService consumerService = Executors.newFixedThreadPool(3);
        for(int i=0;i<3;i++) {
            consumerService.execute(new Consumer(semaphore));
        }
        SUtil.sleepForSeconds(3);
        semaphore.release();
    }

}
