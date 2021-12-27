package xjt.algo.util.skiplist;
/**
 *TODO:手写跳表
 *
 */
public class SkipList<T> {
    private static final int MAX_LEVEL = 16;
    private static final float SKIPLIST_P = 0.5f;
    private int levelCount = 1;
    private Node<T> head = new Node<T>();  // 带头链表
    public void insert(T value) {
        int level = randomLevel();
        Node<T> newNode = new Node<T>();
        newNode.data = value;
        newNode.maxLevel = level;
        Node<T> update[] = new Node[level];
        for (int i = 0; i < level; ++i) {
            update[i] = head;
        }

        // record every level largest value which smaller than insert value in update[]
        Node p = head;
        for (int i = level - 1; i >= 0; --i) {
            while (p.forwards[i] != null && p.forwards[i].data.hashCode() < value.hashCode()) {
                p = p.forwards[i];
            }
            update[i] = p;// use update save node in search path
        }

        // in search path node next node become new node forwords(next)
        for (int i = 0; i < level; ++i) {
            newNode.forwards[i] = update[i].forwards[i];
            update[i].forwards[i] = newNode;
        }

        // update node hight
        if (levelCount < level) {
            levelCount = level;
        }
    }
    public Node find(T value) {
        Node p = head;
        for (int i = levelCount - 1; i >= 0; --i) {
            while (p.forwards[i] != null && p.forwards[i].hashCode() < value.hashCode()) {
                p = p.forwards[i];
            }
        }
        if (p.forwards[0] != null && p.forwards[0].data == value) {
            return p.forwards[0];
        } else {
            return null;
        }
    }
    public T get(T value){
        Node<T> node = find(value);
        return node!=null?node.data:null;
    }
    /**
     * 理论来讲，一级索引中元素个数应该占原始数据的 50%，二级索引中元素个数占 25%，三级索引12.5% ，一直到最顶层。
     * 因为这里每一层的晋升概率是 50%。对于每一个新插入的节点，都需要调用 randomLevel 生成一个合理的层数。
     * 该 randomLevel 方法会随机生成 1~MAX_LEVEL 之间的数，且 ：
     * 50%的概率返回 1
     * 25%的概率返回 2
     * 12.5%的概率返回 3 ...
     * */
    private int randomLevel() {
        int level = 1;
        while (Math.random() < SKIPLIST_P && level < MAX_LEVEL) {
            level += 1;
        }
        return level;
    }
    private static class Node<T>{
        private T data;
        private Node forwards[] = new Node[MAX_LEVEL];
        private int maxLevel = 0;
    }

    public static void main(String[] args) {
        SkipList<String> skipList = new SkipList<>();
        skipList.insert("a");
        skipList.insert("a");
        skipList.insert("c");
        skipList.insert("d");

        System.out.println("a="+skipList.find("a"));
        System.out.println("a="+skipList.get("a"));
        System.out.println("b="+skipList.find("b"));
    }
}
