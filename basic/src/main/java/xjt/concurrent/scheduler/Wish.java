package xjt.concurrent.scheduler;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Wish implements Delayed {
    int seconds = 0;
    long start = System.currentTimeMillis();
    public Wish(int seconds){
        this.seconds = seconds;
    }
    @Override
    public long getDelay(TimeUnit unit) {
        return  unit.convert((start+seconds*1000)-System.currentTimeMillis(),TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }
}
