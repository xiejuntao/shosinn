package xjt.algo;

import org.junit.jupiter.api.Test;

public class TestLinkedList {
    @Test
    public void test(){
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");
        list.add("g");
        list.add("h");
        System.out.println("list.size()="+list.size());
        //list.printAll();
        System.out.println(list.get(2));
        System.out.println(list.get(-2));
        System.out.println(list.get(0));
        System.out.println(list.get(4));
        System.out.println(list.get(41));
    }
}
