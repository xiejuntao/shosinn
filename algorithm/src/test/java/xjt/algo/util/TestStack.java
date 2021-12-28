package xjt.algo.util;

import org.junit.jupiter.api.Test;
import xjt.algo.util.list.Stack;

public class TestStack {
    @Test
    public void testPushAndPop(){
        Stack<String> stack = new Stack<>();
        stack.push("a");
        stack.push("b");
        stack.push("c");
        System.out.println(stack.getTop());
/*        System.out.println("s="+stack.pop());
        System.out.println("s="+stack.pop());
        stack.push("d");
        stack.push("e");*/
/*        System.out.println("s="+stack.pop());
        System.out.println("s="+stack.pop());
        System.out.println("s="+stack.pop());
        System.out.println("s="+stack.pop());*/
        stack.printAll();
    }
}
