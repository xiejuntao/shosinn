package xjt.scalableio.classic;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class ClassicClient {
    public static void main(String[] args) {
        int tasks = 72047;
        CountDownLatch countDownLatch = new CountDownLatch(tasks);
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        for(int i=0;i<tasks;i++) {
            int finalI = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket socket = new Socket();
                        socket.connect(new InetSocketAddress(2047), 2000);
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        String req = "connect-" + finalI;
                        bufferedWriter.write(req);
                        bufferedWriter.close();
                        log.info("connect`req={}", req);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            });
        }
        try {
            log.info("await");
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
