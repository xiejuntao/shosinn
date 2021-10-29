package xjt.utils;

import java.util.concurrent.TimeUnit;

public class SUtil {
    public static void sleepForSeconds(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void print(String log,Object...params){
        System.out.println("["+Thread.currentThread().getName()+"]"+"["+System.currentTimeMillis()+"]"+String.format(log,params));
    }
}
