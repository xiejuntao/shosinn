package xjt.q.queue;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
@Slf4j
public class TraverseTree {
    public static List<List<Integer>> levelOrderV2(TreeNode root){
        List<List<Integer>> list = new ArrayList<>();
        List<TreeNode> curLevel = new ArrayList<>();
        if(root!=null){
            curLevel.add(root);
        }
        while (curLevel.size()>0){
            List<TreeNode> nextLevel = new ArrayList<>();
            List<Integer> curResult = new ArrayList<>();
            for (TreeNode cur: curLevel){
                curResult.add(cur.val);
                if(cur.left!=null){
                    nextLevel.add(cur.left);
                }
                if(cur.right!=null){
                    nextLevel.add(cur.right);
                }
            }
            curLevel =nextLevel;
            list.add(curResult);
        }
        return list;
    }
    public static List<List<Integer>> levelOrder(TreeNode root){
        Queue<TreeNode> queue = new LinkedList<>();
        if(root!=null){
            queue.offer(root);
        }
        List<List<Integer>> list = new LinkedList<>();
        while (queue.size()>0){
            final int qSize = queue.size();
            List<Integer> tmpList = new LinkedList<>();
            for(int i=0;i<qSize;i++){
                TreeNode cur = queue.poll();
                tmpList.add(cur.val);

                if(cur.left!=null){
                    queue.offer(cur.left);
                }
                if(cur.right!=null){
                    queue.offer(cur.right);
                }
            }
            list.add(tmpList);
        }
        return list;
    }
    public static TreeNode getRootTreeNode(){
        TreeNode rootTreeNode = new TreeNode();
        rootTreeNode.val = 3;
        TreeNode rootLeft = new TreeNode();
        rootLeft.val = 9;
        TreeNode rootRight = new TreeNode();
        rootRight.val = 8;
        rootTreeNode.left = rootLeft;
        rootTreeNode.right = rootRight;

        TreeNode tLeft = new TreeNode();
        tLeft.val = 6;
        TreeNode tRight = new TreeNode();
        tRight.val = 7;
        rootRight.left = tLeft;
        rootRight.right = tRight;
        return rootTreeNode;
    }

    public static void main(String[] args) {
        TreeNode rootTreeNode = getRootTreeNode();
        log.info("levelOrder`list={}",levelOrder(rootTreeNode));
        log.info("levelOrderV2`list={}",levelOrderV2(rootTreeNode));
    }
}
