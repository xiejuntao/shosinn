package xjt.algo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class LinkedNode<T> {
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
