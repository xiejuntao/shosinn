package xjt.algo.util.dfa;


import java.util.HashMap;
import java.util.Map;

public class DFA {
    private Map<String, Object> rootChildNodes = new HashMap<>();
    public DFA build(String line){
        Map<String,Object> p = rootChildNodes;
        DFANode dfaNode = null;
        for (int i=0;i<line.length();i++){
            String c = String.valueOf(line.charAt(i));
            dfaNode = (DFANode) p.get(c);
            if(dfaNode==null){
                dfaNode = new DFANode();
                p.put(String.valueOf(c),dfaNode);
            }
            p = dfaNode.setEnd(false);
        }
        dfaNode.setEnd(true);
        return this;
    }

    private static final String IS_END = "isEnd";
    private static class DFANode{
        private Map<String,Object> map = new HashMap<>();
        public DFANode() {
            map.put(IS_END,0);
        }
        private Map<String,Object> setEnd(boolean isEnd){
            map.put(IS_END,isEnd?1:0);
            return map;
        }
        private Map<String,Object> setChild(String data,DFANode dfaNode){
            map.put(data,dfaNode);
            return map;
        }
    }

    public static void main(String[] args) {
        DFA dfa = new DFA();
        dfa.build("秋意浓");
        dfa.build("秋风起时");
        dfa.build("风吹云散");
        System.out.println(dfa);
    }
}
