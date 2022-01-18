package xjt.q.queue;

public class ArrayCircularQueueV2<E> implements CircularQueue<E>{
    private int front = 0;
    private int rear = 0;
    private Object[] array = null;
    private int capacity = 0;
    public ArrayCircularQueueV2(int capacity){
        this.capacity = capacity + 1;//初始化队列，此时队列中的元素为capacity+1。
        array = new Object[capacity + 1];
    }
    @Override
    public boolean enQueue(E e) {
        if(isFull()){
            return false;
        }
        array[rear] = e;
        rear = (rear+1)%capacity;//后一个
        return true;
    }

    @Override
    public boolean deQueue() {
        if(isEmpty()){
            return false;
        }
        front = (front+1)%capacity;
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
        return front == rear;
    }

    @Override
    public boolean isFull() {
        return (rear+1)%capacity == front;
    }
}
