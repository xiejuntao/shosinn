package xjt.id.snowflake;
/**
 * https://github.com/relops/snowflake
 * https://time.geekbang.org/column/article/12806?cid=100006701
 * */
public class TestSnowflake {
    public static void main(String[] args) {
        int node = 1;
        Snowflake s = new Snowflake(node);
        long id = s.next();
        System.out.println(id);
        System.out.println(Long.MAX_VALUE);
    }
}
