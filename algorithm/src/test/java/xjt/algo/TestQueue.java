package xjt.algo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import xjt.algo.util.list.Queue;

@Slf4j
public class TestQueue {
    @Test
    public void testQueue(){
        Queue<String> queue = new Queue<>();

        queueString(queue);

        enqueue(queue,"a");
        enqueue(queue,"b");
        enqueue(queue,"c");
        queueString(queue);

        dequeue(queue);
        size(queue);
        queueString(queue);
        dequeue(queue);
        size(queue);
        queueString(queue);

        enqueue(queue,"d");
        enqueue(queue,"e");
        size(queue);
        queueString(queue);

        dequeue(queue);
        dequeue(queue);
        dequeue(queue);
        dequeue(queue);
        dequeue(queue);
        size(queue);
        queueString(queue);

        enqueue(queue,"f");
        enqueue(queue,"g");
        enqueue(queue,"h");
        size(queue);
        queueString(queue);

        dequeue(queue);
        dequeue(queue);
        dequeue(queue);
        dequeue(queue);
        dequeue(queue);
        dequeue(queue);
        size(queue);
        queueString(queue);
    }
    private void enqueue(Queue queue,Object object){
        queue.enqueue(object);
        log.info("enqueue="+object);
    }
    private void dequeue(Queue queue){
        log.info("dequeue="+queue.dequeue());
    }
    private void size(Queue queue){
        log.info("queue size="+queue.size());
    }
    private void queueString(Queue queue){
        log.info("queue string="+queue.toQueueString());
        log.info("queue head="+queue.headString());
        log.info("queue reverse string="+queue.toQueueReverseString());
        log.info("queue tail="+queue.tailString());
    }



}
