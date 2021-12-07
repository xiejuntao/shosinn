package xjt.type;

public class TestIntCache {
    //-Djava.lang.Integer.IntegerCache.high=65535
    public static void main(String[] args) {
        Integer m = 123;
        Integer n = 123;
        System.out.println(m == n); // true
        Integer q = 2046;
        Integer p = 2046;
        System.out.println(q == p);
    }
}
