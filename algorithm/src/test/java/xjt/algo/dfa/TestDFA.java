package xjt.algo.dfa;

import org.junit.jupiter.api.Test;
import xjt.algo.util.dfa.DFATree;

import java.util.HashSet;
import java.util.Set;

public class TestDFA {
    @Test
    public void testDFATree(){
        Set<String> list = new HashSet<>();
        list.add("人");
        list.add("天地");
        list.add("众生");
        list.add("人生几何");
        list.add("人生到处应何似");
        list.add("几处春秋");
        DFATree dfaTree = new DFATree(list);
        Set<String> matchs = dfaTree.getSensitiveWord("见自己，见天地，见众生",0);
        for (String s:matchs) {
            System.out.println(s);
        }
    }
    /**
     *{
     *  "天":{
     *      "isEnd":0
     *      ,"地"：{“isEnd”:1}
     *  }
     *  ,"人":{
     *      "isEnd":0
     *      ,"生":{
     *          "isEnd":0
     *          ,几:{
     *              "isEnd":0
     *              ,"何":{"isEnd":1}
     *          }
     *          ,"到":{
     *              "isEnd":0
     *              ,"处":{
     *                  "isEnd":0
     *                  ,"应":{
     *                      "isEnd":0
     *                      ,"何":{
     *                          "isEnd":0
     *                          ,"似":{"isEnd":1}
     *                      }
     *                  }
     *              }
     *          }
     *      }
     *  }
     *  ,"众":{
     *      "isEnd":0
     *      ,"生":{"isEnd":1}
     *  }
     *  ,"几":{
     *      "isEnd":0
     *      ,"处":{
     *          "isEnd":0
     *          ,"春":{
     *              "isEnd":0
     *              ,"秋":{"isEnd":1}
     *          }
     *      }
     *  }
     *}
     *
     *
     * */
}
