package xjt.algo;

import org.junit.jupiter.api.Test;

public class TestLinkedList {
    @Test
    public void test(){
        LinkedList<String> list = new LinkedList<>();
        System.out.println(list.add("a"));
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");
        list.add("g");
        list.add("h");
        System.out.println("list.size()="+list.size());
        list.printAll();
    }
}
