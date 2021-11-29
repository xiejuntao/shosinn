package xjt.algo.dp;
/**
 * 动态规划：最短路径
 * 假设我们有一个 n 乘以 n 的矩阵 w[n][n]。矩阵存储的都是正整数。棋子起始位置在左上角，终止位置在右下角。
 * 我们将棋子从左上角移动到右下角。每次只能向右或者向下移动一位。从左上角到右下角，会有很多不同的路径可以走。
 * 我们把每条路径经过的数字加起来看作路径的长度。那从左上角移动到右下角的最短路径长度是多少呢？
 * */
public class MinDistDP {
    /**
     * @param matrix 矩阵数据
     * @param n 矩阵大小
     * */
    public int miniDist(int[][] matrix,int n){
        int[][] states = new int[n][n];
        int sum = 0;
        for(int j=0;j<n;j++){//初始化states的第一行数据
            sum += matrix[0][j];
            states[0][j] = sum;
        }
        sum = 0;
        for(int i=0;i<n;i++){//初始化states第一列数据
            sum += matrix[i][0];
        }
        return 0;
    }
}
