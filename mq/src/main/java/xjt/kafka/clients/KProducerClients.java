package xjt.kafka.clients;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;

@Slf4j
public class KProducerClients {
    private KafkaProducer<String, String> producer;
    public KProducerClients(String brokers){
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(props);
    }
    public boolean produce(String topic,String key,String value){
        try {
            producer.send(new ProducerRecord<>(topic, key, value), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        log.info("produce msg callback success`topic={}`key={}`value={}`metadata={}`", topic, key, value, metadata);
                    } else {
                        log.error("produce msg callback error`topic={}`key={}`value={}", topic, key, value, exception);
                    }
                }
            });
            return true;
        }catch (Exception e){
            log.error("produce error`topic={}`key={}`value={}",topic, key, value,e);
            return false;
        }
    }
}
