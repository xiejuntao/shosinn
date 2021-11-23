package xjt.algo.tree;

import org.junit.jupiter.api.Test;

public class BSTressTest {
    @Test
    public void test(){
        BSTree bsTree = new BSTree();
        bsTree.insert(4);
        bsTree.insert(2);
        bsTree.insert(5);
        bsTree.insert(1);
        bsTree.insert(3);
        bsTree.insert(6);

        System.out.println(bsTree);

        //assertEquals(2,bsTree.find(2).getData());
        //assertNull(bsTree.find(9));

        bsTree.delete(2);
        bsTree.delete(6);
        bsTree.delete(5);
    }
}
