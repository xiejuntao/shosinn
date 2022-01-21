package xjt.mds.kafka;

import xjt.mds.kafka.request.KafkaRequestType;

import java.nio.channels.SelectionKey;

public class KafkaRequest {
    public KafkaRequestType type;
    public SelectionKey selectionKey;
    public String req;
    public KafkaRequest(SelectionKey selectionKey,KafkaRequestType type,String req) {
        this.selectionKey = selectionKey;
        this.type = type;
        this.req = req;
    }
}
