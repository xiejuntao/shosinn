package xjt.concurrent.scheduler;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.DelayQueue;
@Slf4j
public class TestScheduler {
    public static void main(String[] args) {
        DelayQueue delayQueue = new DelayQueue();
        delayQueue.offer(new Wish(3));
        delayQueue.offer(new Wish(7));
        log.info("p0");
        try {
            log.info("",delayQueue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("p1");

        try {
            log.info(""+delayQueue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info(""+delayQueue.poll());
        log.info("p3");
    }
}
