package xjt.q.ll;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 带假头的链表
 * https://kaiwu.lagou.com/course/courseInfo.htm?courseId=685#/detail/pc?id=6693
 * */
@Slf4j
public class DummyLinkedList<T> {
    static class ListNode<T>{
        public T value;
        public ListNode next = null;
        public ListNode(T value){
            this.value = value;
        }
        public ListNode(){
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ListNode<?> listNode = (ListNode<?>) o;
            return Objects.equals(value, listNode.value);
        }
        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }
    private ListNode dummy,tail;
    private int length;
    public DummyLinkedList(){
        this.dummy = new ListNode();
        this.tail = this.dummy;
        this.length = 0;
    }
    public DummyLinkedList add(T t){
        tail.next = new ListNode(t);
        tail = tail.next;
        length = length + 1;
        return this;
    }
    public DummyLinkedList addAtHead(T t){
        ListNode<T> p = new ListNode<>(t);
        p.next = dummy.next;//将p.next指向第一个结点。
        dummy.next = p;//dummy.next指向新结点，使之变成第一个结点。
        if(tail==dummy){
            tail = p;//注意动静结合原则，添加结点时，注意修改tail指针。
        }
        length = length + 1;
        return this;
    }
    /**
     *  在查找索引值为index的结点时，大多数情况下，返回指定结点的前面一个结点pre更加有用
     *  1、通过pre.next就可以访问到想访问的结点，若没找到，则pre.next为空。
     *  2、通过pre可以方便完成后续操作，比如在target前面insert一个新结点，或将target结点从链表中移出去
     * */
    private ListNode getPreNode(int index){
        //初始化front与back,分别一前一后
        ListNode front = dummy.next;
        ListNode back = dummy;
        for(int i=0;i<index && front!=null;i++){
            back = front;
            front = front.next;
        }
        //把back作为pre返回
        return back;//如果为空表（只有一个假头的链表），此时pre/back指向dummy，即返回的prev指针总是有效的。
    }
    public T get(int index){
        if(index<0||index>=length){
            return null;
        }
        return (T) getPreNode(index).next.value;
    }
    public DummyLinkedList add(int index,T t){
        if(index>=length){//大于或等于链表原长度，则插入在末尾。
            add(t);
        } else if(index<=0){
            addAtHead(t);//小于或等于0，则插入在头部
        }else {
            ListNode p = new ListNode(t);
            ListNode pre = getPreNode(index);
            p.next = pre.next;//记住操作顺序！！
            pre.next = p;
            length = length+1;
        }
        return this;
    }
    public void delete(int index){
        if(index<=0||index>=length){
            return;
        }
        ListNode pre = getPreNode(index);
        pre.next = pre.next.next;
        if(index==(length-1)){
            tail = pre;//如果要删除的是最后一个结点,则需要更改tail指针
        }
        length = length - 1;
    }
    public static void main(String[] args) {
/*        ListNode<Integer> node = new ListNode<>(2);
        ListNode<Integer> node2 = new ListNode<>(2);
        System.out.println(node.equals(node2));
        System.out.println(node==node2);*/
        DummyLinkedList<Integer> dummyLinkedList = new DummyLinkedList<>();
        dummyLinkedList.add(2);
        dummyLinkedList.add(0);
        dummyLinkedList.add(4);
        dummyLinkedList.add(6);
        dummyLinkedList.addAtHead(0);
        dummyLinkedList.addAtHead(0);
        dummyLinkedList.add(0,11);
        dummyLinkedList.add(2,22);
        log.info("0={}",dummyLinkedList.get(0));
        log.info("10={}",dummyLinkedList.get(10));
        log.info("2={}",dummyLinkedList.get(2));
        log.info("7={}",dummyLinkedList.get(7));
    }
}
