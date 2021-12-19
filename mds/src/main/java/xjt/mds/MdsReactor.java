package xjt.mds;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class MdsReactor implements Runnable{
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private MdsAcceptor mdsAcceptor;
    public MdsReactor(ServerSocketChannel serverSocketChannel) {
        try {
            this.serverSocketChannel = serverSocketChannel;
            this.selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            mdsAcceptor = new MdsAcceptor(serverSocketChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void select(){
        while (true){
            try {
                int size = selector.select(); // 阻塞，直到有通道事件就绪
                if(size>0){
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()){
                        SelectionKey selectionKey = iterator.next();
                        if(selectionKey.isAcceptable()){
                            mdsAcceptor.accept(selector,selectionKey);
                        }else{
                            dispatch(selectionKey);
                        }
                    }
                    selectionKeys.clear();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private void dispatch(SelectionKey selectionKey) throws IOException {
        MdsHandler mdsHandler = (MdsHandler) selectionKey.attachment();
        mdsHandler.handle(selectionKey);
    }

    @Override
    public void run() {
        select();
    }
}
