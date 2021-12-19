package xjt.mds;

import java.util.concurrent.ConcurrentHashMap;

public class MdsLayer {
    public static ConcurrentHashMap<String,String> map = new ConcurrentHashMap<>();
    public static void put(String key,String val){
        map.put(key,val);
    }
    public static String get(String key){
        return map.get(key);
    }
}
