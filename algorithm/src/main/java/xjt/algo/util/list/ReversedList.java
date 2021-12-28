package xjt.algo.util.list;

public class ReversedList<T> extends LinkedList{
    /**
     * head->A->B->..>tail
     * 改为
     * head<-A<-B<-..<tail
     *
     * */
    public void reverse(){
       LinkedNode cur = this.getHead();
       LinkedNode pre = null;
        while (cur!=null){
            if(pre==null){
                pre = cur;
                //pre.setNext(null);
            }else{
                pre.setNext(null);
                LinkedNode tmp = cur.getNext();
                cur.setNext(pre);
            }
            cur = cur.getNext();
        }
        //this.setHead(cur);
    }
    public LinkedNode getPreNode(LinkedNode node){
        LinkedNode head = this.getHead();
        LinkedNode pre = head;
        LinkedNode next = head.getNext();
        while (next!=null){
            if(node.getData().equals(next.getData())){
                return pre;
            }
            pre = next;
            next = next.getNext();
        }
        return null;
    }
}
