package xjt.algo.trie;

import org.junit.jupiter.api.Test;

public class TestTrie {
    @Test
    public void test(){
        Trie trie = new Trie().build("天凉好个秋a").build("天凉了").build("秋天到了");
        System.out.println(trie.find("秋",false));//true
        System.out.println(trie.find("天",true));//false
        System.out.println(trie.find("天凉了",true));//true
        System.out.println(trie.find("凉了",false));//false
    }
}
