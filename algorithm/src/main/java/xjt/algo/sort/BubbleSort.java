package xjt.algo.sort;
/**
 * 冒泡排序
 * 每次冒泡操作都会对相邻的两个元素进行比较，看是否满足大小关系要求。
 * 如果不满足就让它俩互换。
 * 一次冒泡会让至少一个元素移动它应该的位置，重复n次，就完成n个数据的排序。
 * */
public class BubbleSort implements ISort {
    @Override
    public int[] sort(int[] array,boolean des) {
        int l = array.length;
        for(int i=0;i<l;i++){
            // 提前退出标志位
            boolean flag = false;
            for (int j=0;j<(l-i-1);j++){
                if(des?(array[j]<array[j+1]):(array[j]>array[j+1])){
                    int tmp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = tmp;
                    // 此次冒泡有数据交换
                    flag = true;
                }
            }
            if(!flag){
                break;
            }
        }
        return array;
    }
}
