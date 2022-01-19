package xjt.mds.kafka;

import lombok.extern.slf4j.Slf4j;
import xjt.concurrent.AdvanceExecutors;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Kafka网络模型
 * 1、网络线程池接收请求放入共享请求队列
 * 2、IO线程池负责从请求队列取出请求，真正处理后将生成的响应发送到网络线程的响应对列中，由对应的网络线程池负责将响应返还给客户端。
 * */
@Slf4j
public class Kafka {
    public LinkedBlockingQueue<KafkaRequest> requestQueue = new LinkedBlockingQueue<>();
    public int netThreads = 3;
    public int ioThreads = 8;
    public void start(int port){
        try {
            ServerSocketChannel serverSocketChannel= ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(port)); // 绑定端口
            // 设置成非阻塞模式
            serverSocketChannel.configureBlocking(false);
            log.info("kafka server started`port={}",port);
            ExecutorService netExecutorService = AdvanceExecutors.newFixedThreadPoolWithQueueSizeByName("kafkaNetThread",netThreads,1000);
            for(int i=0;i<netThreads;i++){
                netExecutorService.execute(new KafkaNetAcceptor(serverSocketChannel,requestQueue));
            }
            ExecutorService workExecutorService = AdvanceExecutors.newFixedThreadPoolWithQueueSizeByName("kafkaWorkThread",ioThreads,1000);
            for(int j=0;j<ioThreads;j++){
                workExecutorService.execute(new KafkaIOWorker(requestQueue));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Kafka().start(19092);
    }
}
