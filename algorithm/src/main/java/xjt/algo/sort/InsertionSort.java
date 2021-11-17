package xjt.algo.sort;
/**
 * 将数据中的数据分为两个区间，已排序区间和未排序区间。
 * 初始已排序区间只有一个元素，就是数据的第一个元素。
 * 插入算法的核心思想是取未排序区间中的元素，在已排序区间中找到合适的插入位置将其插入，并保证已排序区间数据一直有序。
 * 重复这个过程，直到未排序区间中元素为空，算法结束。
 * */
public class InsertionSort implements ISort{
    @Override
    public int[] sort(int[] array, boolean des) {
        int sortIndex = 0;
        return new int[0];
    }
}
