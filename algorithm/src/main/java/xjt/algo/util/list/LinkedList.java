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
            LinkedNode<T> preNode = getPreLinkedNode(tail);
            preNode.setNext(node);
        }
        tail = node;
        size = size + 1;
        //LinkedNode<T> linkedNode = new LinkedNode<T>(t);
        return size;
    }
    public int size(){
        return size;
    }
    public LinkedNode<T> getPreLinkedNode(LinkedNode<T> node){
        LinkedNode<T> tmp = head;
        while (tmp!=null){
            if(tmp.getData().equals(node.getData())){
                return tmp;
            }else {
                tmp = tmp.getNext();
            }
        }
        return null;
    }
    public void printAll(){
        if(size==0){
            return;
        }
        LinkedNode<T> node = head;
        while (node!=null){
            System.out.println(node);
            node = node.getNext();
        }

    }
    public T get(int i){
        if(size<=0||i<0){
            return null;
        }
        LinkedNode<T> node = head;
        int index = 0;
        while (node!=null){
            //System.out.println(node);
            if(index==i){
                return node.getData();
            }
            node = node.getNext();
            index=index+1;
        }
        return null;
    }
    @Data
    private static class LinkedNode<T> {
        private T data;
        private LinkedNode<T> next;
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
