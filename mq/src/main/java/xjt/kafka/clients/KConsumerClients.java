package xjt.kafka.clients;

import xjt.concurrent.AdvanceExecutors;

import java.util.concurrent.ExecutorService;

public class KConsumerClients {
    private KConsumerConfig kConsumerConfig;
    public KConsumerClients(String brokers) {
        kConsumerConfig = new KConsumerConfig();
        kConsumerConfig.BOOTSTRAP_SERVERS_CONFIG = brokers;

    }
    public void consume(String topic,String groupId,KConsumerCallback kConsumerCallback){
        multiConsumeThread(topic,groupId,1,kConsumerCallback);
    }
    public void multiConsumeThread(String topic,String groupId,int threads, KConsumerCallback kConsumerCallback){
        kConsumerConfig.GROUP_ID_CONFIG = groupId;
        ExecutorService consumerExecutorService = AdvanceExecutors.newFixedThreadPoolWithQueueSizeByName("multiConsumeThread",threads,100);
        for(int i=0;i<threads;i++) {
            consumerExecutorService.execute(new KConsumerRunnable(kConsumerConfig, topic, kConsumerCallback));
        }
    }
    public void multiConsumeWithWorkerThread(String topic,String groupId,int threads,int perWorkers,KConsumerCallback kConsumerCallback){
        kConsumerConfig.GROUP_ID_CONFIG = groupId;
        ExecutorService consumerExecutorService = AdvanceExecutors.newFixedThreadPoolWithQueueSizeByName("multiConsumeWithWorkerThread",threads,100);
        for(int i=0;i<threads;i++) {
            consumerExecutorService.execute(new KConsumerRunnable(kConsumerConfig, topic, kConsumerCallback,perWorkers));
        }
    }
}