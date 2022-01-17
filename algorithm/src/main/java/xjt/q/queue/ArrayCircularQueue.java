package xjt.q.queue;

public class ArrayCircularQueue<E> implements CircularQueue<E>{
    private int size = 0;
    private int front = 0;
    private int rear = 0;
    private int capacity = 0;
    private Object[] array = null;
    public ArrayCircularQueue(int capacity){
        this.capacity = capacity;
        array = new Object[capacity];
    }
    @Override
    public boolean enQueue(E e) {
        if(isFull()){
            return false;
        }
        array[rear] = e;
        rear = (rear+1)%capacity;//后一个
        size = size + 1;
        return true;
    }

    @Override
    public boolean deQueue() {
        if(isEmpty()){
            return false;
        }
        Object ret = array[front];
        front = (front+1)%capacity;
        size = size-1;
        return true;
    }

    @Override
    public E front() {
        if(isEmpty()){
            return null;
        }
        return (E)array[front];
    }

    @Override
    public E rear() {
        if(isEmpty()){
            return null;
        }
        int tail = (rear - 1 + capacity) % capacity;
        return (E)array[tail];
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public boolean isFull() {
        return size==capacity;
    }
}
