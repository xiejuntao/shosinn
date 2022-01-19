package xjt.kafka.clients;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface KConsumerCallback {
    public void handle(ConsumerRecord consumerRecord);
}
