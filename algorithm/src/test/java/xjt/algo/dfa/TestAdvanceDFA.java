package xjt.algo.dfa;

import org.junit.jupiter.api.Test;
import xjt.algo.util.dfa.AdvanceDFA;

import java.util.HashSet;
import java.util.Set;

public class TestAdvanceDFA {
    @Test
    public void testAdvanceDFA(){
        Set<String> list = new HashSet<>();
        list.add("人");
        list.add("天地");
        list.add("众生");
        list.add("人生几何");
        list.add("人生到处应何似");
        list.add("几处春秋");
        AdvanceDFA dfaTree = new AdvanceDFA(list);
        Set<String> matchs = dfaTree.getMatchWords("见自己，见天地，见众生",1);
        for (String s:matchs) {
            System.out.println(s);
        }
    }
}
