package xjt.algo;

import lombok.Data;

@Data
public class LinkedList<T> {
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;
    public LinkedList(){

    }
    public int add(T t){
        LinkedNode<T> node = new LinkedNode<T>(t);
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

    public static void main(String[] args){
        LinkedList<String> list = new LinkedList<String>();
        list.add("a");
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
