package xjt.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
@Slf4j
public class KProducer {
    public static void main(String[] args) {
        String topic = "wq";
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG,3000);
        props.put(ProducerConfig.METADATA_MAX_AGE_CONFIG,1000);

        //props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,true);//幂等性，空间换时间。只支持分区、会话作用域。无效？
        //props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG,"x0");//开启事务。


        List<String> interceptors = new ArrayList<>();
        interceptors.add("xjt.kafka.interceptors.OnSendInterceptor");
        //props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,interceptors);
        Producer<String, String> producer = new KafkaProducer<>(props);
        doSend(producer,topic);
        doSend(producer,topic);
        //producer.close();
    }
    public static void doSend(Producer<String, String> producer, String topic) {
        for (int i = 0; i < 20; i++) {
            String key = Integer.toString(i);
    /*                producer.send(new ProducerRecord<String, String>(topic, key, "gg" + key), new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        log.info("send`msg={}", metadata);
                    }
                });*/
            String value = "v" + key;
            producer.send(new ProducerRecord<>(topic, key, value));
            log.info("send msg`key={}`value={}", key, value);

            producer.send(new ProducerRecord<>(topic, key, value));
            log.info("send msg`key={}`value={}", key, value);

            producer.send(new ProducerRecord<>(topic, key, value));
            log.info("send msg`key={}`value={}", key, value);

        }
    }
}
