package xjt.algo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
@Slf4j
public class TestQueue {
    @Test
    public void testQueue(){
        Queue<String> queue = new Queue<>();

        queueString(queue);

        push(queue,"a");
        push(queue,"b");
        push(queue,"c");
        queueString(queue);

        pop(queue);
        size(queue);
        queueString(queue);
        pop(queue);
        size(queue);
        queueString(queue);

        push(queue,"d");
        push(queue,"e");
        size(queue);
        queueString(queue);

        pop(queue);
        pop(queue);
        pop(queue);
        pop(queue);
        pop(queue);
        size(queue);
        queueString(queue);

        push(queue,"f");
        push(queue,"g");
        push(queue,"h");
        size(queue);
        queueString(queue);

        pop(queue);
        pop(queue);
        pop(queue);
        pop(queue);
        pop(queue);
        pop(queue);
        size(queue);
        queueString(queue);
    }
    private void push(Queue queue,Object object){
        queue.enqueue(object);
        log.info("queue push="+object);
    }
    private void pop(Queue queue){
        log.info("queue pop="+queue.pop());
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
