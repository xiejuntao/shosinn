package xjt.algo.backtracking;
/**
 * 八皇后问题:
 * 有一个 8x8 的棋盘，希望往里放 8 个棋子（皇后），每个棋子所在的行、列、对角线都不能有另一个棋子。
 * 八皇后问题就是期望找到所有满足这种要求的放棋子方式。
 * */
public class EightQueens {
    private int[] result = new int[8];//全局或成员变量,下标表示行,值表示queen存储在哪一列。
    public void cal(int row){//调用方式:cal(0)。
        if(row == 8){//8个棋子都放置好了，打印结果。
            printResult(result);
            return;
        }
        for(int column = 0; column < 8; ++column){//每一行都有8中放法。
            if(isPerfect(row,column)){
                result[row] = column;//完美，第row行的棋子放到了column列。
                cal(row+1);//考察下一列
            }else{

            }
        }
    }
    private boolean isPerfect(int row,int column){
        int leftup = column - 1;
        int rightup = column + 1;
        for(int i = row-1; i >= 0; --i){//逐行往上考察每一行
            if(result[i] == column){//考察往上每一行的column列是否有棋子。
                return false;
            }
            if(leftup>0){//考察左上对角线是否有棋子。
                if(result[i] == leftup) {
                    return false;
                }
            }
            if(rightup < 8){//考察右上对角线是否有棋子
                if(result[i] == rightup){
                    return false;
                }
            }
            --leftup; ++rightup;
        }
        return true;
    }
    int c = 0;
    private void printResult(int[] result){
        for(int row = 0;row < 8;row++){
            for(int column = 0; column < 8;column++){
                if(column == result[row]){
                    System.out.print("Q ");
                }else{
                    System.out.print("* ");
                }
            }
            System.out.println();
        }
        System.out.println("ccccccccccccccc:"+c++);
    }

    public static void main(String[] args) {
        EightQueens eightQueens = new EightQueens();
        eightQueens.cal(0);
    }
}
