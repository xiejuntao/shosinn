package xjt.concurrent.disruptor;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * https://tech.meituan.com/2016/11/18/disruptor.html
 * Disruptor的设计方案
 * Disruptor通过以下设计来解决队列速度慢的问题：
 *
 * 环形数组结构
 * 为了避免垃圾回收，采用数组而非链表。同时，数组对处理器的缓存机制更加友好。
 *
 * 元素位置定位
 * 数组长度2^n，通过位运算，加快定位的速度。下标采取递增的形式。不用担心index溢出的问题。index是long类型，即使100万QPS的处理速度，也需要30万年才能用完。
 *
 * 无锁设计
 * 每个生产者或者消费者线程，会先申请可以操作的元素在数组中的位置，申请到之后，直接在该位置写入或者读取数据。
 *
 * 下面忽略数组的环形结构，介绍一下如何实现无锁设计。整个过程通过原子变量CAS，保证操作的线程安全。
 *
 * ---------------------------------------------------------------
 *一个生产者
 * 写数据
 *
 * 生产者单线程写数据的流程比较简单：
 *
 * 申请写入m个元素；
 * 若是有m个元素可以入，则返回最大的序列号。这儿主要判断是否会覆盖未读的元素；
 * 若是返回的正确，则生产者开始写入元素。
 * 多个生产者
 * 多个生产者的情况下，会遇到“如何防止多个线程重复写同一个元素”的问题。Disruptor的解决方法是，每个线程获取不同的一段数组空间进行操作。
 * 这个通过CAS很容易达到。只需要在分配元素的时候，通过CAS判断一下这段空间是否已经分配出去即可。
 *
 * 但是会遇到一个新问题：如何防止读取的时候，读到还未写的元素。Disruptor在多个生产者的情况下，
 * 引入了一个与Ring Buffer大小相同的buffer：available Buffer。
 * 当某个位置写入成功的时候，便把availble Buffer相应的位置置位，标记为写入成功。
 * 读取的时候，会遍历available Buffer，来判断元素是否已经就绪。
 * ---------------------------------------------------------------
 *
 *
 * */
@Slf4j
public class TestDisruptor {
    public static void main(String[] args) throws InterruptedException {
        Disruptor<OrderEvent> disruptor = new Disruptor<>(
                OrderEvent::new,
                1024 * 1024,
                Executors.defaultThreadFactory(),
                // 这里的枚举修改为多生产者
                ProducerType.MULTI,
                new YieldingWaitStrategy()
        );
        disruptor.handleEventsWithWorkerPool(new WorkHandler<OrderEvent>() {
            @Override
            public void onEvent(OrderEvent event) throws Exception {

            }
        });
        //disruptor.handleEventsWithWorkerPool(new OrderEventHandler(), new OrderEventHandler(), new OrderEventHandler());
        disruptor.start();
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();
        OrderEventProducer eventProducer = new OrderEventProducer(ringBuffer);
        // 创建一个线程池，模拟多个生产者
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (int i = 0; i < 100; i++) {
            //fixedThreadPool.execute(() -> eventProducer.onData(UUID.randomUUID().toString()));
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    disruptor.publishEvent(new EventTranslator<OrderEvent>() {
                        @Override
                        public void translateTo(OrderEvent event, long sequence) {
                            String id = UUID.randomUUID().toString();
                            event.setId(id);
                            log.info("publishEvent`no={}`id={}",atomicInteger.getAndIncrement(),id);
                        }
                    });
                }
            });
        }
    }
}
