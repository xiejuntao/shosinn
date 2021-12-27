package xjt.algo.util.tree;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 二叉查找树
 * */
public class BSTree {
    private BSTressNode rootNode;
    public void insert(int data){
        if(rootNode==null){
            rootNode = new BSTressNode(data);
            return;
        }
        BSTressNode pNode = rootNode;
        while (pNode!=null){
            if(data<pNode.getData()){
                if(pNode.getLeftNode()==null){
                    pNode.setLeftNode(new BSTressNode(data));
                    return;
                }else{
                    pNode = pNode.getLeftNode();
                }
            }else{
                if(pNode.getRightNode()==null){
                    pNode.setRightNode(new BSTressNode(data));
                    return;
                }else {
                    pNode = pNode.getRightNode();
                }
            }
        }
    }
    public BSTressNode find(int data){
        BSTressNode pNode = rootNode;
        while (pNode!=null){
            if(data<pNode.getData()){
                pNode = pNode.getLeftNode();
            }else if(data>pNode.getData()){
                pNode = pNode.getRightNode();
            }else{
                return pNode;
            }
        }
        return null;
    }
    /**
     * 删除操作的步骤如下：
     *
     * 查找到要删除的节点。
     * 如果待删除的节点是叶子节点，则直接删除。
     * 如果待删除的节点不是叶子节点，则先找到待删除节点的中序遍历的后继节点，用该后继节点的值替换待删除的节点的值，然后删除后继节点。
     * @see https://tech.meituan.com/2016/12/02/redblack-tree.html
     * */
    public void delete(int data){
        BSTressNode dNode = rootNode;//dNode指向要删除的节上海火车站，初始化指向根节点
        BSTressNode parentNode = null;
        while (dNode!=null&&dNode.getData()!=data){
            parentNode = dNode;
            if(data>dNode.getData()){
                dNode = dNode.getRightNode();
            }else{
                dNode = dNode.getLeftNode();
            }
        }
        if(dNode==null){
            return;
        }
        //要删除的节点有两个子节点
        if(dNode.getLeftNode()!=null && dNode.getRightNode()!=null){
            BSTressNode minNode = dNode.getRightNode();
            BSTressNode minParentNode = dNode;
            while (minNode.getLeftNode()!=null){
                minParentNode = minNode;
                minNode = minNode.getLeftNode();
            }
            dNode.setData(minNode.getData());
            dNode = minNode;
            parentNode = minParentNode;
            //return;
        }
        //要删除节点是叶子节点或者仅有一个子节点
        BSTressNode childNode;
        if(dNode.getLeftNode()!=null){
            childNode = dNode.getLeftNode();
        }else if(dNode.getRightNode()!=null){
            childNode = dNode.getRightNode();
        }else {
            childNode = null;
        }

        if(parentNode==null){//删除的是根节点
            rootNode = childNode;
        }else if(parentNode.getLeftNode()==dNode){
            parentNode.setLeftNode(childNode);
        }else{
            parentNode.setRightNode(childNode);
        }
        
    }
    @Data
    @RequiredArgsConstructor
    public static class BSTressNode{
        @NonNull
        private int data;
        private BSTressNode leftNode;
        private BSTressNode rightNode;
    }
}
