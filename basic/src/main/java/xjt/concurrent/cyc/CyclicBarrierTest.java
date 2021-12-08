package xjt.concurrent.cyc;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {
    public static void main(String[] args){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                System.out.println("done");
            }
        });
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        //executorService.
        for(int i=0;i<3;i++){
            executorService.execute(new ExecutorThread(i,cyclicBarrier));
        }
        //ExecutorService executorService = Executors.newFixedThreadPool(3);
        for(int i=0;i<3;i++){
            executorService.execute(new ExecutorThread(i,cyclicBarrier));
        }
        executorService.shutdown();
    }
}
