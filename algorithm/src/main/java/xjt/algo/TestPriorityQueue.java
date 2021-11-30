package xjt.algo;

import java.util.Comparator;
import java.util.PriorityQueue;

public class TestPriorityQueue {
    public static void main(String[] args) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        priorityQueue.offer(5);
        priorityQueue.offer(7);
        priorityQueue.offer(1);
        priorityQueue.offer(9);
        priorityQueue.offer(6);
        priorityQueue.offer(2);

        //System.out.println(priorityQueue.peek());
        System.out.println(priorityQueue.poll());
    }
}
