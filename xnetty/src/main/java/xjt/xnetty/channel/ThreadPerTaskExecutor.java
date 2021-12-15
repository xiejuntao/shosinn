package xjt.xnetty.channel;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

public class ThreadPerTaskExecutor implements Executor {
    private final ThreadFactory threadFactory;
    public ThreadPerTaskExecutor(ThreadFactory threadFactory) {
        if (threadFactory == null) {
            throw new NullPointerException("threadFactory");
        }
        this.threadFactory = threadFactory;
    }
    @Override
    public void execute(Runnable command) {
        threadFactory.newThread(command);
    }
}
