package xjt.concurrent.semaphore;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
@Slf4j
public abstract class Pool<T> {
    protected int MAX_AVAILABLE = 100;
    protected List<T> items = new ArrayList<>();
    protected boolean[] used = new boolean[MAX_AVAILABLE];
    private final Semaphore available;

    public Pool(int MAX_AVAILABLE) {
        this.MAX_AVAILABLE = MAX_AVAILABLE;
        available = new Semaphore(MAX_AVAILABLE, true);
    }

    public abstract void init();
    public T getItem() throws InterruptedException {
        available.acquire();
        log.info("acquire");
        return getNextAvailableItem();
    }
    public void putItem(T x){
        if (markAsUnused(x)){
            available.release();
        }
    }
    private T getNextAvailableItem() {
        for (int i = 0; i < MAX_AVAILABLE; ++i){
            if (!used[i]) {
                used[i] = true;
                return items.get(i);
            }
        }
        return null; // not reached
    }
    protected synchronized boolean markAsUnused(T item){
        if(!items.contains(item)){
            items.add(item);
        }
        for (int i = 0; i < MAX_AVAILABLE; ++i) {
            if (item == items.get(i)) {
                if (used[i]) {
                    used[i] = false;
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }
}
