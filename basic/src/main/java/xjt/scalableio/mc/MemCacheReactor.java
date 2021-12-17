package xjt.scalableio.mc;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
/**
 * 参考模型https://time.geekbang.org/column/article/8805
 * */
@Slf4j
public class MemCacheReactor implements Runnable{
    private Selector selector;
    private ConcurrentLinkedQueue<MemCacheWorker> workers = new ConcurrentLinkedQueue<MemCacheWorker>();
    public MemCacheReactor() {
        try {
            this.selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void register(MemCacheWorker worker) {
        workers.add(worker);
        selector.wakeup();//唤起run的select阻塞
    }
    @Override
    public void run() {
        while (!Thread.interrupted()){
            workes();
            selector();
        }
    }
    public void workes(){
        MemCacheWorker worker;
        while ((worker = workers.poll()) != null) {
            try {
                worker.getSocketChannel().configureBlocking(false); // 设置非阻塞
                // Optionally try first read now
                SelectionKey selectionKey = worker.getSocketChannel().register(selector, SelectionKey.OP_READ); // 注册通道
                selectionKey.attach(worker);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void selector(){
        try {
            log.info("reactor select");
            int size = selector.select(); // 阻塞，直到有通道事件就绪
            log.info("reactor`size={}",size);
            if(size>0) {
                Set<SelectionKey> selected = selector.selectedKeys(); // 拿到就绪通道 SelectionKey 的集合
                Iterator<SelectionKey> it = selected.iterator();
                while (it.hasNext()) {
                    SelectionKey selectionKey = it.next();
                    MemCacheWorker memCacheWorker = (MemCacheWorker) selectionKey.attachment();
                    if (memCacheWorker != null) {
                        memCacheWorker.handle(selectionKey);
                    }
                }
                selected.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
