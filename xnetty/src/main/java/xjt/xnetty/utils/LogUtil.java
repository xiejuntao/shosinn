package xjt.xnetty.utils;

public class LogUtil {
    public static void log(String format,Object... params){
        System.out.println(String.format("[" + Thread.currentThread().getId() + "]" + format,params));
    }
}
