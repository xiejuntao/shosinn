package xjt.algo.util.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

public class Queue<T>{
    @Getter
    private QueueNode<T> tail;
    @Getter
    private QueueNode<T> head;
    private int size;
    public int enqueue(T t){
        QueueNode<T> newNode = new QueueNode<>(t,null,null);
        if(size==0){
            head = newNode;
        }else {
            QueueNode<T> tmpNode = tail;
            tmpNode.setNext(newNode);
            newNode.setPre(tmpNode);
            if (size == 1) {
                head = tail;
            }
        }
        tail = newNode;
        size = size + 1;
        return size;
    }
    public T dequeue(){
        if(size<=0){
            return null;
        }
        T t = head.getData();
        if(size==1){
            head = null;
            tail = null;
        }else{
            head = head.getNext();
            head.setPre(null);
        }
        size = size -1;
        return t;
    }
    public int size(){
        return size;
    }
    public String toQueueString(){
        DataStringBuilder stringBuilder = new DataStringBuilder();
        QueueNode<T> queueNode = head;
        while(queueNode!=null){
            stringBuilder.append(queueNode.getData());
            queueNode = queueNode.getNext();
        }
        return stringBuilder.toString();
    }
    public String toQueueReverseString(){
        DataStringBuilder stringBuilder = new DataStringBuilder();
        QueueNode<T> queueNode = tail;
        while (queueNode!=null){
            stringBuilder.append(queueNode.getData());
            queueNode = queueNode.getPre();
        }
        return stringBuilder.toString();
    }
    public String tailString(){
        if(tail==null){
            return "";
        }
        return "QueueNode{" +
                "data=" + tail.getData() +
                // ", pre=" + pre +
                ", pre=" + tail.getPre() +
                '}';
    }
    public String headString(){
        if(head==null){
            return "";
        }
        return "QueueNode{" +
                "data=" + head.getData() +
                // ", pre=" + pre +
                ", next=" + head.getNext() +
                '}';
    }
    private static class DataStringBuilder{
        private StringBuilder stringBuilder = new StringBuilder();
        private final static String LIMITER = ",";
        public void append(Object object){
            stringBuilder.append(object).append(LIMITER);
        }
        @Override
        public String toString(){
            int lastIndexOfDelimiter = stringBuilder.lastIndexOf(LIMITER);
            return lastIndexOfDelimiter==-1?""
                    :stringBuilder.deleteCharAt(lastIndexOfDelimiter).toString();
        }
    }
    @Data
    @AllArgsConstructor
    private static class QueueNode<T>{
        private T data;
        private QueueNode<T> pre;
        private QueueNode<T> next;

        @Override
        public String toString() {
            return "QueueNode{" +
                    "data=" + data +
                   // ", pre=" + pre +
                    ", next=" + next +
                    '}';
        }
    }
}
