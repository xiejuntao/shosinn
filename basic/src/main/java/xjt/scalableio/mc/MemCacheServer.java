package xjt.scalableio.mc;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * 内存缓存服务器
 * 支持set param value和get param
 * */
@Slf4j
public class MemCacheServer {
    ServerSocketChannel serverSocket = null;
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
            MemCacheAcceptor memCacheAcceptor = new MemCacheAcceptor();
            memCacheAcceptor.start(serverSocket,selector);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
