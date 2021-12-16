package xjt.concurrent;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

public class AdvanceExecutors {
    protected ListeningExecutorService executorService = MoreExecutors.listeningDecorator(newFixedThreadPoolWithQueueSize(20,10000));
    public static ExecutorService newFixedThreadPoolWithQueueSize(int nThreads, int queueSize) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                5000L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(queueSize, true), new ThreadPoolExecutor.CallerRunsPolicy());
    }
    public static ExecutorService newFixedThreadPoolWithQueueSizeByName(String name,int nThreads, int queueSize) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                5000L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(queueSize, true)
                ,new ThreadFactoryBuilder().setNameFormat(name+"-%d").build()
                , new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
