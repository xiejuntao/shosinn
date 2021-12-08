package xjt.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * ReentrantLock使用阻塞队列
 * */
@Slf4j
public class BlockingList<T> {
    private List<T> dataList;
    private Lock lock;
    private Condition notEmptyCondition;

    public BlockingList(){
        dataList = new ArrayList<T>();
        lock = new ReentrantLock();
        notEmptyCondition = lock.newCondition();
    }
    public T take() throws InterruptedException {
        lock.lockInterruptibly();
        log.info("take");
        try {
            while (dataList.isEmpty()){
                log.info("take wait");
                notEmptyCondition.await();
            }
            return dataList.remove(0);
        }finally {
            log.info("take unlock");
            lock.unlock();
        }
    }
    public void add(T t){
        try {
            lock.lock();
            log.info("add");
            dataList.add(t);
            notEmptyCondition.signal();//通知等待的线程，非空条件已经满足
        }finally{
            log.info("add unlock");
            lock.unlock();
        }
    }
}
