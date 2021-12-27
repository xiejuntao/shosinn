package xjt.algo.trie;

import org.junit.jupiter.api.Test;
import xjt.algo.util.trie.Trie;

import java.util.HashSet;
import java.util.Set;

public class TestTrie {
    @Test
    public void test(){
        Trie trie = new Trie().build("天凉好个秋a").build("天凉了").build("秋天到了");
        System.out.println(trie.find("秋",false));//true
        System.out.println(trie.find("天",true));//false
        System.out.println(trie.find("天凉了",true));//true
        System.out.println(trie.find("凉了",false));//false
    }
    @Test
    public void testMatch(){
        Set<String> list = new HashSet<>();
        list.add("人");
        list.add("天地");
        list.add("众生");
        list.add("人生几何");
        list.add("人生到处应何似");
        list.add("几处春秋");
        Trie trie = new Trie(list);
        Set<String> matchs = trie.getMatchWords("见自己，见天地，见众生",1);
        for (String s:matchs) {
            System.out.println(s);
        }
    }
}
