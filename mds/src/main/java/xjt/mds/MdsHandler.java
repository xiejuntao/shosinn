package xjt.mds;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

@Slf4j
public class MdsHandler {
    private SocketChannel socketChannel;
    private MdsStreamHandler mdsStreamHandler;
    public MdsHandler(SocketChannel socketChannel){
        this.socketChannel = socketChannel;
        mdsStreamHandler = new MdsStreamHandler(socketChannel,MdsConstants.SINGLETIP);
    }
    public void handle(SelectionKey selectionKey) throws IOException {
       if (selectionKey.isReadable()) {
            mdsStreamHandler.read(new MdsStreamHandler.MdsReadStreamCallback() {
                @Override
                public void readCompleted(String req) {
                    process(req);
                    selectionKey.interestOps(SelectionKey.OP_WRITE);
                }
            });
        }
        if (selectionKey.isWritable()) {
            mdsStreamHandler.write(new MdsStreamHandler.MdsWriteStreamCallback() {
                @Override
                public void closeSession() throws IOException {
                    selectionKey.channel().close();
                }
                @Override
                public void writeCompleted() {
                    selectionKey.interestOps(SelectionKey.OP_READ);
                }
            });
        }
    }
    public void process(String req){
        String res = "NULL";
        if(req.startsWith("set")){
            String[] params = req.split(" ");
            String key = params[1];
            String val = params[2];
            MdsLayer.put(key,val);
            res = "OK";
        }
        if(req.startsWith("get")){
            String[] params = req.split(" ");
            String key = params[1];
            String val = MdsLayer.get(key);
            if(val!=null){
                res = val;
            }
        }
        log.info("process`req={}`res={}",req,res);
        mdsStreamHandler.write(res);
    }
}
