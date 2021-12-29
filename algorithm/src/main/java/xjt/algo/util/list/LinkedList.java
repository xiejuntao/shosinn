package xjt.algo.util.list;

import lombok.Data;

@Data
public class LinkedList<T> {
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;
    public LinkedList(){

    }
    public int add(T t){
        LinkedNode<T> node = new LinkedNode<>(t);
        if(size()==0){
            head = node;
        }else {
            tail.next = node;
        }
        tail = node;
        size = size + 1;
        return size;
    }
    public int size(){
        return size;
    }
    public void printAll(){
        if(size==0){
            return;
        }
        StringBuilder sb = new StringBuilder();
        LinkedNode<T> node = head;
        while (node!=null){
            sb.append(node.data+"  ");
            node = node.next;
        }
        System.out.println(sb);
    }
    public LinkedNode getNode(int i){
        if(size<=0||i<0){
            return null;
        }
        LinkedNode<T> node = head;
        int index = 0;
        while (node!=null){
            //System.out.println(node);
            if(index==i){
                return node;
            }
            node = node.next;
            index=index+1;
        }
        return null;
    }
    public T get(int i){
        LinkedNode<T> node = getNode(i);
        if(node!=null){
            return node.data;
        }
        return null;
    }
    public static class LinkedNode<T> {
        public T data;
        public LinkedNode<T> next;
        public LinkedNode(T t){
            this.data = t;
        }
        @Override
        public String toString() {
            return "LinkedNode{" +
                    "data=" + data +
                    ", next=" + next +
                    '}';
        }
    }
}
