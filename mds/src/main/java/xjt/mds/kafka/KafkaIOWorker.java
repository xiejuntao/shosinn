package xjt.mds.kafka;

import lombok.extern.slf4j.Slf4j;

import java.nio.channels.SelectionKey;
import java.util.concurrent.LinkedBlockingQueue;
@Slf4j
public class KafkaIOWorker implements Runnable{
    private LinkedBlockingQueue<KafkaRequest> requestQueue;
    private LinkedBlockingQueue<KafkaResponse> responseQueue = new LinkedBlockingQueue<>();
    public KafkaIOWorker(LinkedBlockingQueue<KafkaRequest> requestQueue){
        this.requestQueue = requestQueue;
    }
    @Override
    public void run() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        KafkaResponse kafkaResponse = responseQueue.take();
                        log.info("take response`res={}",kafkaResponse.res);
                        kafkaResponse.selectionKey.attach(kafkaResponse.res);
                        kafkaResponse.selectionKey.interestOps(SelectionKey.OP_WRITE);
                        kafkaResponse.selectionKey.selector().wakeup();
                    } catch (InterruptedException e) {
                        log.error("notify write response error",e);
                    }
                }
            }
        },"responseQueueThread").start();
        log.info("start response thread");
        log.info("start request handle thread");
        while (true){
            try {
                KafkaRequest kafkaRequest = requestQueue.take();
                log.info("take request`req={}",kafkaRequest.req);
                KafkaResponse kafkaResponse = handle(kafkaRequest);
                log.info("put response`req={}`res={}",kafkaRequest.req,kafkaResponse.res);
                responseQueue.put(kafkaResponse);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public KafkaResponse handle(KafkaRequest kafkaRequest){
        String res = kafkaRequest.req.toUpperCase();
        log.info("handle request`req={}`res={}",kafkaRequest.req,res);
        return new KafkaResponse(res,kafkaRequest.selectionKey);
    }
}
