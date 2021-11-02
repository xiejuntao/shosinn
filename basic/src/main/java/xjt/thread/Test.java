package xjt.thread;

import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args){
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
        System.out.println("stash");
    }
    public static void sleepForSeconds(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
