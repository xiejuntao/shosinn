package xjt.scalableio;

import lombok.extern.slf4j.Slf4j;

import java.io.EOFException;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
@Slf4j
public class XThreadPoolHandler extends XHandler{
    static final int PROCESSING = 4;
    static Executor workPool = Executors.newFixedThreadPool(5);
    public XThreadPoolHandler(Selector selector, SocketChannel socketChannel) throws IOException {
        super(selector,socketChannel);
    }

    @Override
    protected synchronized void read() throws IOException {
        //super.read();
        input.clear(); // 清空接收缓冲区
        int n = socketChannel.read(input);
        if(inputIsComplete(n)) {
            CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
            workPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        String requestContent = request.toString().toUpperCase(); // 请求内容
                        byte[] response = requestContent.getBytes(StandardCharsets.UTF_8);
                        output.put(response);
                        log.info("after process");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    completableFuture.complete(SENDING);
                }
            });
            completableFuture.thenAcceptAsync(new Consumer<Integer>() {
                @Override
                public void accept(Integer tmpState) {
                    state = tmpState;
                    selectionKey.interestOps(SelectionKey.OP_WRITE);
                    log.info("active write");
                }
            });
            log.info("next");
        }
    }

    protected void processAndHandOff(){
        CompletableFuture<Void> completableFuture = new CompletableFuture();
        completableFuture.thenAccept(new Consumer<Void>() {
            @Override
            public void accept(Void unused) {
                // 最后的发送还是交给 Reactor 线程处理
                state = SENDING;
                selectionKey.interestOps(SelectionKey.OP_WRITE);

                log.info("after process");
            }
        });
        log.info("start process");
        processInFuture(completableFuture);
        log.info("end process");
    }
    protected void processInFuture(CompletableFuture completableFuture){
        try {
            super.process();
        } catch (EOFException e) {
            e.printStackTrace();
        }
        completableFuture.complete(null);
    }
    class Processer implements Runnable{
        @Override
        public void run() {
            processAndHandOff();
        }
    }
}