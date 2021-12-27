package xjt.algo.util.hash;

import com.google.common.hash.Hashing;

import java.math.BigDecimal;

public class HashTest {
    public static void main(String[] args) {
/*        String a = "aaaaaaaaa";
        String b = "b";
        String c = "c";
        printHash(a);
        printHash(b);
        printHash(c);*/

//        LinkedHashMap<Integer, Integer> m = new LinkedHashMap<>(10, 0.75f, true);
//        m.put(3, 11);
//        m.put(1, 12);
//        m.put(5, 23);
//        m.put(2, 22);
//        m.put(3, 26);
//        m.get(5);
///*        Iterator<Integer> iterator = map.keySet().iterator();
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());//3 5 2 1
//        }*/
//        for (Map.Entry e : m.entrySet()) {
//            System.out.println(e.getKey());
//        }

        System.out.println(Integer.MAX_VALUE);

        System.out.println(BigDecimal.valueOf(Math.pow(2,32)));

        System.out.println(Hashing.consistentHash(123,4));//一致性hash
        System.out.println(Hashing.consistentHash(122223,4));
        System.out.println(Hashing.consistentHash(33333,4));


    }
    public static void printHash(Object obj){
        System.out.println(obj+"="+obj.hashCode());
    }
}
