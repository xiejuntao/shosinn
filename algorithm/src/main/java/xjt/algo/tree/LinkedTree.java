package xjt.algo.tree;

import lombok.Data;

public class LinkedTree<T> {
    private TreeNode<T> rootNode;
    private TreeNode<T> lastNode;
    public void initRoot(T t){
        rootNode = new TreeNode<>();
        rootNode.setData(t);
    }
    public void addLeftNode(T data){
        TreeNode<T> newNode = new TreeNode<>();
        newNode.setData(data);
        //newNode
    }
    public LinkedTree addNode(T data){
        TreeNode<T> newNode = new TreeNode<>();
        newNode.setData(data);
        if(rootNode==null){
            rootNode = newNode;
            lastNode = rootNode;
            return this;
        }
        if(lastNode == rootNode){
            rootNode.setLeftNode(newNode);
            newNode.setParentNode(rootNode);
            lastNode = newNode;
            return this;
        }
        if(lastNode.getParentNode().getRightNode()==null){
            lastNode.getParentNode().setRightNode(newNode);
            newNode.setParentNode(lastNode.getParentNode());
            lastNode = newNode;
            return this;
        }else{
            if(lastNode.getParentNode().getParentNode().getLeftNode()==null){
                lastNode.getParentNode().getParentNode().setLeftNode(newNode);
                newNode.setParentNode(lastNode.getParentNode().getParentNode());
                lastNode=newNode;
            }
        }
        if(lastNode.getParentNode().getLeftNode().getLeftNode()==null){
            lastNode.getParentNode().getLeftNode().setLeftNode(newNode);
            newNode.setParentNode(lastNode.getParentNode().getLeftNode());
            lastNode = newNode;
            return this;
        }
        return this;
    }
    @Data
    private static class TreeNode<T>{
        private T data;
        private TreeNode<T> parentNode;
        private TreeNode<T> leftNode;
        private TreeNode<T> rightNode;
    }
}
