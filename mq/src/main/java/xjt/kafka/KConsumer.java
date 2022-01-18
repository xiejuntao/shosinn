package xjt.kafka;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * https://kafka.apache.org/documentation/#consumerconfigs
 * */
@Slf4j
public class KConsumer {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"xx2");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");//latest
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,10);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);
        properties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG,5000);//The maximum delay between invocations of poll() when using consumer group management.

        properties.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG,"xjt.kafka.interceptors.OnConsumeInterceptor");

        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(properties);

        consumer.subscribe(Collections.singleton("wq"));
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));
                log.info("consume record`size={}", records.count());
                int i = 0;
                for (ConsumerRecord consumerRecord : records) {
                    log.info("consume detail record`key={}`value={}", consumerRecord.key(), consumerRecord.value());
                    Thread.sleep(TimeUnit.SECONDS.toMillis(6));
                    if (++i % 2 == 1) {
/*                        consumer.commitAsync(new OffsetCommitCallback() {
                            @Override
                            public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                                log.info("commit complete`offsets={}", offsets);
                            }
                        });*/
                        consumer.commitSync();
                    }
                }
            }
        }catch (Exception e){
            log.error("consume error",e);
        }finally {
            try {
                consumer.commitSync();
            }finally {
                consumer.close();
            }
        }
    }
}
