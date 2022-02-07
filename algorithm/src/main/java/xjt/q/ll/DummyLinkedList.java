package xjt.q.ll;
/**
 * 带假头的链表
 * https://kaiwu.lagou.com/course/courseInfo.htm?courseId=685#/detail/pc?id=6693
 * */
public class DummyLinkedList<T> {
    class ListNode<T>{
        public T value;
        public ListNode next = null;
        public ListNode(T value){
            this.value = value;
        }
        public ListNode(){
        }
    }
    private ListNode dummy,tail;
    private int length;
    public DummyLinkedList(){
        this.dummy = new ListNode();
        this.tail = this.dummy;
        this.length = 0;
    }
    public void addAtTail(T t){
        tail.next = new ListNode(t);
        tail = tail.next;
        length = length + 1;
    }
    public void addAtHead(T t){

    }
}
