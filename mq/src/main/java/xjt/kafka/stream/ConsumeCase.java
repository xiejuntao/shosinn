package xjt.kafka.stream;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import xjt.kafka.KConfig;
import xjt.kafka.clients.KConsumerCallback;
import xjt.kafka.clients.KConsumerClients;
@Slf4j
public class ConsumeCase {
    public static void main(String[] args) {
        String topic = "streams-wordcount-output";
        KConsumerClients kConsumerClients = new KConsumerClients(KConfig.BROKERS);
        kConsumerClients.consume(topic, "t", new KConsumerCallback() {
            @Override
            public void handle(ConsumerRecord consumerRecord) {
                log.info("consume`topic={}``value={}",topic,consumerRecord.value());
            }
        });
    }
}
