package xjt.mds.kafka;

import java.nio.channels.SelectionKey;

public class KafkaResponse {
    public String res;
    public SelectionKey selectionKey;

    public KafkaResponse(String res, SelectionKey selectionKey) {
        this.res = res;
        this.selectionKey = selectionKey;
    }
}
