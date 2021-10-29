package xjt.thread;

import xjt.utils.SUtil;

import java.util.concurrent.*;

public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true){
//                    System.out.println("t");
//                    sleepForSeconds(1);
//                }
//            }
//        });
//        t.setDaemon(true);
//        t.start();
//        sleepForSeconds(2);
//        System.out.println("done");
        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                for (int i=0;i<3;i++){
                    SUtil.print("going");
                    SUtil.sleepForSeconds(1);
                }
                return "天凉好个秋";
            }
        });
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(futureTask);
        //futureTask.run();
        SUtil.print("pre");
//        try {
//            SUtil.print(futureTask.get(1,TimeUnit.SECONDS));
//        } catch (TimeoutException e) {
//            e.printStackTrace();
//        }
        SUtil.print(futureTask.get());
        SUtil.print("done");
    }
}
