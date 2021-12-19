package xjt.mds;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
@Slf4j
public class Mds {
    ServerSocketChannel serverSocket = null;
    public Mds(int port){
        try {
            serverSocket= ServerSocketChannel.open();
            serverSocket.socket().bind(new InetSocketAddress(port)); // 绑定端口
            // 设置成非阻塞模式
            serverSocket.configureBlocking(false);
            log.info("server started`port={}",port);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void startSingleReactorSingleThread(){
        log.info("start ractor thread waiting accept");
        new Thread(new MdsReactor(serverSocket),"redis-reactor").start();
    }
    public void startSingleReactorMutilThread(){
        log.info("start single reactor multi thread ");
    }
}
