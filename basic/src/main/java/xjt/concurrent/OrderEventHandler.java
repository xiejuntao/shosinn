package xjt.concurrent;

import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderEventHandler implements EventHandler<OrderEvent>{
    @Override
    public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) {
        log.info("event: {}, sequence: {}, endOfBatch: {}", event, sequence, endOfBatch);
    }
/*    @Override
    public void onEvent(OrderEvent event) {
        log.info("event: {}", event);
    }*/
}