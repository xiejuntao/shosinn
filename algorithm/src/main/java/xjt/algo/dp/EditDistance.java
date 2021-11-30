package xjt.algo.dp;
/**
 * 动态规划：编辑距离
 * 如何量化两个字符串之间的相似程度呢？有一个非常著名的量化方法，那就是编辑距离（Edit Distance）。
 * 编辑距离指的就是，将一个字符串转化成另一个字符串，需要的最少编辑操作次数（比如增加一个字符、删除一个字符、替换一个字符）。
 * */
public class EditDistance {
    /**
     * 回溯算法：莱文斯坦距离(Levenshtein distance):允许增加、删除、替换字符这三个编辑操作
     * 回溯是一个递归处理的过程。如果 a[i]与 b[j]匹配，我们递归考察 a[i+1]和 b[j+1]。
     * 如果 a[i]与 b[j]不匹配，那我们有多种处理方式可选：
     * 1、可以删除 a[i]，然后递归考察 a[i+1]和 b[j]；
     * 2、可以删除 b[j]，然后递归考察 a[i]和 b[j+1]；
     * 3、可以在 a[i]前面添加一个跟 b[j]相同的字符，然后递归考察 a[i]和 b[j+1];
     * 4、可以在 b[j]前面添加一个跟 a[i]相同的字符，然后递归考察 a[i+1]和 b[j]；
     * 5、可以将 a[i]替换成 b[j]，或者将 b[j]替换成 a[i]，然后递归考察 a[i+1]和 b[j+1]。
     * */
    private char[] a = "aaabcmitcmu".toCharArray();
    private char[] b = "cccccccddfamtacnu".toCharArray();
    private int n = a.length;
    private int m = b.length;
    private int minDist = Integer.MAX_VALUE;
    //调用方式 levenshteinBt(0, 0, 0);
    public void levenshteinBt(int i,int j,int edist){
        if(i==n || j==m){
            //System.out.println("");
            if(i<n){
                edist += (n-i);
            }
            if(j<m){
                edist += (m-j);
            }
            if(edist<minDist){
                minDist=edist;
            }
            return;
        }
        if(a[i]==b[j]){
            levenshteinBt(i+1,j+1,edist);//两个字符匹配
        }else {
            levenshteinBt(i+1,j,edist+1);//删除a[i]或b[j]前添加一个字符
            levenshteinBt(i,j+1,edist+1);//删除b[j]或a[i]前添加一个字符
            levenshteinBt(i+1,j+1,edist+1);//将a[i]和b[j]替换这相同字符
        }
    }

    public static void main(String[] args) {
        EditDistance editDistance = new EditDistance();
        editDistance.levenshteinBt(0,0,0);
        System.out.println(editDistance.minDist);
    }
}
