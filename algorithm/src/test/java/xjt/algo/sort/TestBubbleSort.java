package xjt.algo.sort;

import org.junit.jupiter.api.Test;

public class TestBubbleSort {
    @Test
    public void sort(){
        BubbleSort bubbleSort = new BubbleSort();
        int[] sortedArray = bubbleSort.sort(new int[]{2,22,3, 9, 1, 4, 12},true);
        for(int i=0;i<sortedArray.length;i++){
            System.out.println(sortedArray[i]);
        }
        sortedArray = bubbleSort.sort(new int[]{2,22,3, 9, 1, 4, 12},false);
        for(int i=0;i<sortedArray.length;i++){
            System.out.println(sortedArray[i]);
        }
    }
}
