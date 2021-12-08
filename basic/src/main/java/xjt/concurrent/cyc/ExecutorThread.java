package xjt.concurrent.cyc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ExecutorThread implements Runnable{
    public int i;
    public CyclicBarrier cyclicBarrier;
    public ExecutorThread(int i,CyclicBarrier cyclicBarrier) {
        this.i = i;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+":"+i);
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
