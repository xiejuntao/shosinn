package xjt.xnetty.loop;

import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.List;

public class NioEventLoopGroup {
    public List<NioEventLoop> list = new ArrayList<NioEventLoop>();
    public NioEventLoopGroup(int threads){
        if(threads<=0){
            threads=1;
        }
        for(int i=0; i<threads; i++){
            list.add(new NioEventLoop());
        }
    }
    public NioEventLoop getNioEventLoop(int index){
        if(list.size()<=0){
            return null;
        }else{
            return list.get(index);
        }
    }
    public int currentThread = 0;
    public NioEventLoop next(){
        if(currentThread>(list.size()-1)){
            currentThread = 0;
        }
        NioEventLoop nioEventLoop = getNioEventLoop(currentThread);
        currentThread = currentThread+1;
        return nioEventLoop;
    }
    public void slavesRun(SelectionKey selectionKey){
        NioEventLoop nioEventLoop = next();
        nioEventLoop.slaveRun(selectionKey);
    }
}
