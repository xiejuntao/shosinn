package xjt.mds.redis;

import xjt.mds.Mds;

public class Redis {
    Mds mds = null;
    public Redis(){
        this(6379);
    }
    public Redis(int port){
        mds = new Mds(port);
    }
    public void start(){
        mds.startSingleReactorSingleThread();
    }
}
