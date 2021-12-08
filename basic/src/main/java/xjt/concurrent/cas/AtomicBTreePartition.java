package xjt.concurrent.cas;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

public class AtomicBTreePartition {

    private static final AtomicLongFieldUpdater<AtomicBTreePartition> lockFieldUpdater =
            AtomicLongFieldUpdater.newUpdater(AtomicBTreePartition.class, "lock");

    private void acquireLock(){
        long t = Thread.currentThread().getId();
        while (!lockFieldUpdater.compareAndSet(this, 0L, t)){
            // 等待一会儿，数据库操作可能比较慢

        }
    }
}
