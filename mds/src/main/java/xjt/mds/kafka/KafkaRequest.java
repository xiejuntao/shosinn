package xjt.mds.kafka;

import java.nio.channels.SelectionKey;

public class KafkaRequest {
    public SelectionKey selectionKey;
    public String req;

    public KafkaRequest(SelectionKey selectionKey, String req) {
        this.selectionKey = selectionKey;
        this.req = req;
    }
}
