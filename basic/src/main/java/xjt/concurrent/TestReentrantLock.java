package xjt.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class TestReentrantLock {
    public static void main(String[] args) throws InterruptedException {
        BlockingList<String> blockingList = new BlockingList<>();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(2));
                blockingList.add("智");
            }
        });
        thread.start();
        thread.start();
        String t = blockingList.take();
        log.info("get " +  t);
    }
    public void b() throws InterruptedException {

    }
}
