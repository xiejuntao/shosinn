package xjt.algo.search;

import org.junit.jupiter.api.Test;

/**
 * 变异二分查找
 * 1、判断目标数据是在左分支还是右分支
 *
 * 2、目标数据在右分支。（关键词，“最后一个”等）
 * 2.1、左侧判断直接更新high。
 * 2.2、右侧先判断命中条件（包括判断最右位置length-1），命中返回middle，否则更新low。
 *
 * 3、目标数据在左分支。（关键词，“第一个”等）
 * 3.1、右侧判断直接更新low。
 * 3.2、左侧先判断命中条件（包括判断最左位置0），命中返回middle，否则更新high。
 * */
public class BiAdvanceSearch {
    public int[] datas = new int[]{1,1,1,3,7,7,7,7,9,10,11,12,15,17,23,31,37,37,43,51,59,59,59,63,71,77,85,89,97,97,97};
    /**
     *变体四：查找最后一个小于等于给定值的元素
     * */
    public int getLastLessEqualIndex(int n){
        int low = 0;
        int length = datas.length;
        int high = length - 1;
        while (low<=high){
            int middle = (low+high)/2;
            if(datas[middle]>n){
                high = middle -1;
            }else{
                if(middle==(length-1)||(datas[middle+1]>n)){
                    return middle;
                }else{
                    low = middle+1;
                }
            }
        }
        return -1;
    }
    @Test
    public void testGetLastLessEqualIndex(){
        System.out.println("length="+datas.length);
        System.out.println(getLastLessEqualIndex(9));
        System.out.println(getLastLessEqualIndex(7));
        System.out.println(getLastLessEqualIndex(97));
        System.out.println(getLastLessEqualIndex(107));
        System.out.println(getLastLessEqualIndex(6));
    }
    /**
     *变体三：查找第一个大于等于给定值的元素
     * */
    public int getFirstGreatEqualIndex(int n){
        int length = datas.length;
        int low = 0;
        int high = length - 1;
        while(low<=high){
           int middle = (low+high)/2;
           if(datas[middle]<n){
               low = middle + 1;
           }else{
               if((middle==0)||datas[middle-1]<n){
                   return middle;
               }else {
                   high = middle - 1;
               }
           }
        }
        return -1;
    }
    @Test
    public void testGetFirstGreatEqualIndex(){
        System.out.println("length="+datas.length);
        System.out.println(getFirstGreatEqualIndex(13));
        System.out.println(getFirstGreatEqualIndex(113));
        System.out.println(getFirstGreatEqualIndex(58));
    }
    /**
     * 变体二：查找最后一个值等于给定值的元素
     * */
    public int getLastEqualIndex(int n){
        int length = datas.length;
        int low = 0;
        int high = length - 1;
        while(low<=high){
            int middle = (low+high)/2;
            if(datas[middle]>n){
                high = middle - 1;
            }else{
                if(datas[middle]==n & ((middle==(length-1))||datas[middle+1]>n)){
                    return middle;
                }else{
                    low = middle + 1;
                }
            }
        }
        return -1;
    }
    @Test
    public void testGetLastEqualIndex(){
        System.out.println("length="+datas.length);
        System.out.println(getLastEqualIndex(13));
        System.out.println(getLastEqualIndex(113));
        System.out.println(getLastEqualIndex(58));

        System.out.println(getLastEqualIndex(1));
        System.out.println(getLastEqualIndex(7));
        System.out.println(getLastEqualIndex(97));
    }
    /**
     * 变体一：查找第一个值等于给定值的元素
     * */
    public int getFirstEqualIndex(int n){
        int length = datas.length;
        int low = 0;
        int high = length - 1;
        while (low<=high){
            int middle = (low+high)/2;
            if(datas[middle]<n){
                low = middle + 1;
            }else {
                if(datas[middle]==n && (middle==0||datas[middle-1]<n)){
                    return middle;
                }else{
                    high = middle - 1;
                }
            }
        }
        return -1;
    }
    @Test
    public void testGetFirstEqualIndex(){
        System.out.println(getFirstEqualIndex(7));
        System.out.println(getFirstEqualIndex(17));
        System.out.println(getFirstEqualIndex(37));
        System.out.println(getFirstEqualIndex(77));
    }
}
