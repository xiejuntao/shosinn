package xjt.scalableio.mc;

import lombok.extern.slf4j.Slf4j;
import xjt.concurrent.AdvanceExecutors;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ExecutorService;

/**
 * 内存缓存服务器
 * 支持set param value和get param
 * */
@Slf4j
public class MemCacheServer {
    ServerSocketChannel serverSocket = null;
    MemCacheReactor[] memCacheReactors = null;
    public final static int REACTORS = 7;
    ExecutorService executorService = AdvanceExecutors.newFixedThreadPoolWithQueueSizeByName("reactor",10, 1000);
    public MemCacheServer(int port){
        try {
            serverSocket= ServerSocketChannel.open();
            serverSocket.socket().bind(new InetSocketAddress(port)); // 绑定端口
            // 设置成非阻塞模式
            serverSocket.configureBlocking(false);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void start(){
        try {
            Selector selector = Selector.open();
            // 注册到 选择器 并设置处理 socket 连接事件
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            MemCacheAcceptor memCacheAcceptor = new MemCacheAcceptor(serverSocket,selector);

            memCacheReactors = new MemCacheReactor[REACTORS];
            for(int i = 0;i< memCacheReactors.length;i++){
                memCacheReactors[i] = new MemCacheReactor();
                executorService.execute(memCacheReactors[i]);
            }

            Distributor distributor = new Distributor(REACTORS);
            memCacheAcceptor.start(new MemCacheAcceptor.AcceptorCallback() {
                @Override
                public MemCacheReactor getMemCacheReator() {
                    return memCacheReactors[distributor.roundrobin()];
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
