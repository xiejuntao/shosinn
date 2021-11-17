package xjt.algo.search;

import org.junit.jupiter.api.Test;

public class BiSearch {
    public int[] datas = new int[]{1,3,7,9,10,11,12,15,17,23,31,37,43,51,59,63,71,77,85,89,97};
    public boolean biSearch(int s){
        int low = 0;
        int high = datas.length -1;
        while (low<=high) {
            int middle = (low+high)/2;
            if (datas[middle] > s) {
                high = middle - 1;
            } else if (datas[middle] < s) {
                low = middle + 1;
            } else {
                return true;
            }
        }
        return false;
    }
    /**
     *递归二分查找
     */
    public boolean recSearch(int s,int low,int high){
        if(low<=high) {
            int middle = (low + high) / 2;
            if (datas[middle] == s) {
                return true;
            } else if (datas[middle] > s) {
                return recSearch(s, low, middle - 1);
            } else if (datas[middle] < s) {
                return recSearch(s, middle + 1, high);
            }
        }
        return false;
    }
    public boolean recSearch(int s){
        return recSearch(s,0,datas.length-1);
    }
    @Test
    public void biSearchTest(){
        System.out.println(biSearch(4));
        System.out.println(biSearch(-4));
        System.out.println(biSearch(43));
        System.out.println(biSearch(1));
        System.out.println(biSearch(111));
        System.out.println(biSearch(97));
    }
    @Test
    public void recSearchTest(){
        System.out.println(recSearch(4));
        System.out.println(recSearch(-4));
        System.out.println(recSearch(43));
        System.out.println(recSearch(1));
        System.out.println(recSearch(111));
        System.out.println(recSearch(97));
    }
}
