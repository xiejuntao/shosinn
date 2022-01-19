package xjt.mds.kafka;

import java.nio.channels.SelectionKey;
import java.util.concurrent.LinkedBlockingQueue;

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
                        kafkaResponse.selectionKey.attach(kafkaResponse.res);
                        kafkaResponse.selectionKey.interestOps(SelectionKey.OP_WRITE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"responseQueueThread").start();
        while (true){
            try {
                KafkaRequest kafkaRequest = requestQueue.take();
                KafkaResponse kafkaResponse = handle(kafkaRequest);
                responseQueue.put(kafkaResponse);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public KafkaResponse handle(KafkaRequest kafkaRequest){
        String res = kafkaRequest.req.toUpperCase();
        return new KafkaResponse(res,kafkaRequest.selectionKey);
    }
}
