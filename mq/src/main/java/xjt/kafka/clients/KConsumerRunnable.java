package xjt.kafka.clients;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import xjt.concurrent.AdvanceExecutors;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

@Slf4j
public class KConsumerRunnable implements Runnable{
    public KConsumerConfig kConsumerConfig;
    public Properties properties = null;
    public String topic = "";
    public KConsumerCallback kConsumerCallback;
    public int worker;
    public boolean multiWorker = false;
    public ExecutorService workerExecutorService;
    public KConsumerRunnable(KConsumerConfig kConsumerConfig,String topic,KConsumerCallback kConsumerCallback){
        this(kConsumerConfig,topic,kConsumerCallback,0);
    }
    public KConsumerRunnable(KConsumerConfig kConsumerConfig,String topic,KConsumerCallback kConsumerCallback,int worker){
        this.kConsumerConfig = kConsumerConfig;
        this.topic = topic;
        this.kConsumerCallback = kConsumerCallback;
        this.worker = 0;
        properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kConsumerConfig.BOOTSTRAP_SERVERS_CONFIG);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,kConsumerConfig.GROUP_ID_CONFIG);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,kConsumerConfig.AUTO_OFFSET_RESET_CONFIG);//latest
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,kConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG);
        if(worker>0){
           workerExecutorService = AdvanceExecutors
                   .newFixedThreadPoolWithQueueSizeByName("consumer_"+kConsumerConfig.GROUP_ID_CONFIG+"_"+topic+"_workers",worker,1000);
           this.multiWorker = true;
        }
    }

    @Override
    public void run() {
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Collections.singleton(topic));
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(kConsumerConfig.POLL_INTERVAL_SECONDS));
                if(records.isEmpty()){
                    continue;
                }
                log.info("consume record`size={}", records.count());
                if(multiWorker){
                    CountDownLatch countDownLatch = new CountDownLatch(records.count());
                    for (ConsumerRecord consumerRecord : records) {
                        log.info("async consume detail record`key={}`value={}", consumerRecord.key(), consumerRecord.value());
                        workerExecutorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                kConsumerCallback.handle(consumerRecord);
                                countDownLatch.countDown();
                            }
                        });
                    }
                    countDownLatch.await();
                }else{
                    for (ConsumerRecord consumerRecord : records) {
                        log.info("sync consume detail record`key={}`value={}", consumerRecord.key(), consumerRecord.value());
                        kConsumerCallback.handle(consumerRecord);
                    }
                }

                if(!kConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG) {
                    consumer.commitAsync();
                }
            }
        }catch (Exception e){
            log.error("consume error",e);
        }finally {
            if(!kConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG) {
                try {
                    consumer.commitSync();
                } catch (Exception e) {
                    log.error("finally manual commit error");
                }
            }
            consumer.close();
        }
    }
}
