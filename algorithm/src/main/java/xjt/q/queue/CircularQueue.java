package xjt.q.queue;

public interface CircularQueue<E> {
    /**进队
     * @param t
     * @return
     */
    public boolean enQueue(E e);
    /**
     * 删除队首
     * */
    public boolean deQueue();
    /**
     *获取队首
     * */
    public E front();
    /**
     * 获取队尾
     * */
    public E rear();
    /**
     * 是否为空
     * */
    public boolean isEmpty();
    /**
     * 是否已满
     * */
    public boolean isFull();
}
