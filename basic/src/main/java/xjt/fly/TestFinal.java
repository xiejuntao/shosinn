package xjt.fly;

import java.util.ArrayList;
import java.util.List;

public class TestFinal {
    /**
     * final 并不等同于 immutable。
     * */
    public static void main(String[] args) {
        final List strList = new ArrayList<>();
        strList.add("Hello");
        strList.add("world");
    }
}
