package xjt.algo.util.list;

import lombok.*;

public class Stack<T> {
    @Setter
    @Getter
    private StackNode<T> top;
    private int size;
    public int size(){
        return size;
    }
    public int push(T t){
        StackNode<T> newNode = new StackNode<>(t,null);
        StackNode<T> tmpNode = top;
        if (tmpNode != null) {
            newNode.setPre(tmpNode);
        }
        top = newNode;
        size = size + 1;
        return size;
    }
    public T pop(){
        if(size<=0){
            return null;
        }
        StackNode<T> tmpNode;
        tmpNode = top;
        top = top.getPre();
        size = size - 1;
        return tmpNode.getData();
    }
    public void printAll(){
        StackNode<T> tmpNode = top;
        while (tmpNode!=null){
            System.out.println(tmpNode);
            tmpNode = tmpNode.getPre();
        }
    }
    @Data
    @AllArgsConstructor
    @ToString
    private static class StackNode<T> {
        private T data;
        private StackNode<T> pre;
    }
}
