package xjt.algo.util.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 堆
 * 1、堆是一个完全二叉树；
 * 2、堆中每一个节点的值都必须大于等于（或小于等于）其子树中每个节点的值。
 * */
public class Heap<T extends Comparable> {
    /**
     * 存储节点的数组，从下标1开始存储数据
     * 数组中下标为 i 的节点的左子节点，就是下标为 i∗2 的节点
     * 右子节点就是下标为 i∗2+1 的节点
     * 父节点就是下标为 2i​ 的节点。
     * */
    private HeapNode[] array;
    /**
     * 堆可以存储的最大数据个数
     * */
    private int capacity;
    /**
     * 堆中已经存储的数据个数
     * */
    @Getter
    private int size;
    public Heap(int capacity){
        this.array = new HeapNode[capacity+1];
        this.capacity = capacity;
        this.size = 0;
    }
    public void insert(T data){
        if(size>=capacity){
            throw new IllegalStateException("heap is fulled");
        }
        size = size + 1;
        HeapNode heapNode = new HeapNode(data);
        array[size] = heapNode;
        int i = size;
        while (i/2>0 && array[i].getData().compareTo(array[i/2].getData())>0){//大顶堆
            this.swap(array,i,i/2);
            i = i/2;
        }
    }
    /**
     * 删除堆顶元素
     * 1、把最后一个节点放到堆顶，然后利用同样的父子节点对比方法。
     * 2、对于不满足父子节点大小关系的，互换两个节点，并且重复进行这个过程，直到父子节点之间满足大小关系为止。
     * */
    public boolean remove(){
        if(size==0){
            return false;
        }
        array[1] = array[size];
        size = size - 1;
        heapify(array,size,1);
        return true;
    }

    private void heapify(HeapNode[] array, int size, int i) {
        while (true){
            int maxPos = i;
            if(i*2<=size && array[i].getData().compareTo(array[i*2].getData())<0){
                maxPos = i*2;
            }
            if(i*2+1<=size && array[maxPos].getData().compareTo(array[i*2+1].getData())<0){
                maxPos = i*2 +1;
            }
            if(maxPos==i){
                break;
            }
            swap(array,i,maxPos);
            i = maxPos;
        }
    }

    public void swap(HeapNode[] array,int i,int p){
        HeapNode tmpNode = array[i];
        array[i] = array[p];
        array[p] = tmpNode;
    }

    @Data
    @AllArgsConstructor
    private static class HeapNode<T extends Comparable>{
        private T data;
    }
}
