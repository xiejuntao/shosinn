package xjt.skiplist;

import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class SDKSkipList {
    public static void main(String[] args) {
        System.out.println("ConcurrentSkipListSet");
        ConcurrentSkipListSet<String> strings = new ConcurrentSkipListSet<>();
        strings.add("a");
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");

        System.out.println(strings.size());
        strings.remove("a");
        System.out.println(strings.pollFirst());
        System.out.println(strings.size());

        System.out.println("ConcurrentSkipList");
        ConcurrentSkipListMap<String,String> map = new ConcurrentSkipListMap<>();
        map.put("a","aa");
        map.put("a","aa");
        map.put("a","bb");
        map.put("b","bb");
        map.put("c","cc");

        //System.out.println(map.get("a"));
/*        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }*/
        Iterator<String> iterator = map.values().iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
