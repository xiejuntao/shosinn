package xjt.semaphore;

import xjt.utils.SUtil;

import java.util.concurrent.Semaphore;

public class Consumer implements Runnable{
    public Semaphore semaphore;
    public Consumer(Semaphore semaphore){
        this.semaphore = semaphore;
    }
    @Override
    public void run() {
        SUtil.print("consumer start");
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SUtil.print("consumer end");
    }
}
