package xjt.concurrent.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class XSynchronizer extends AbstractQueuedSynchronizer {
    public XSynchronizer(int permits){
        super.setState(permits);
    }
    public void acquire(){
        //super.acquireSharedInterruptibly(1);
    }
    public void release(){

    }
}
