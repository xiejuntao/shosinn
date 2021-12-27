package xjt.algo.util.dfa;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class AdvanceDFA {
    private DFANode rootNode = new DFANode(true);
    public AdvanceDFA(){}
    public AdvanceDFA(Set<String> lines){
        for (String line:lines) {
            build(line);
        }
    }
    public AdvanceDFA build(String line){
        DFANode p = rootNode;
        DFANode dfaNode = null;
        for (int i=0;i<line.length();i++){
            String c = String.valueOf(line.charAt(i));
            dfaNode = (DFANode) p.get(c);
            if(dfaNode==null){
                dfaNode = new DFANode(false);
                p.setChild(String.valueOf(c),dfaNode);
            }
            p = dfaNode;
        }
        dfaNode.setEnd(true);
        return this;
    }
    /**
     * 获取文字中的匹配词列表:暴力匹配
     *
     * @param txt       文字
     * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
     * @return
     */
    public Set<String> getMatchWords(String txt, int matchType) {
        Set<String> matchWordList = new HashSet<String>();
        for (int i = 0; i < txt.length(); i++) {
            int length = checkMatchWord(txt, i, matchType);    //判断是否包含敏感字符
            if (length > 0) {    //存在,加入list中
                matchWordList.add(txt.substring(i, i + length));
                i = i + length - 1;    //减1的原因，是因为for会自增
            }
        }
        return matchWordList;
    }
    public static int minMatchTYpe = 1;      //最小匹配规则
    public static int maxMatchType = 2;      //最大匹配规则
    public int checkMatchWord(String txt, int beginIndex, int matchType) {
        boolean flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
        int matchFlag = 0;     //匹配标识数默认为0
        int resultMatcherFlag = 0;     //匹配标识数默认为0
        String word = "";
        DFANode p = rootNode;
        for (int i = beginIndex; i < txt.length(); i++) {
            word = String.valueOf(txt.charAt(i));
            p = p.getChild(word);//获取指定key
            if (p != null) {     //存在，则判断是否为最后一个
                matchFlag++;     //找到相应key，匹配标识+1
                if (p.isEndNode()) {       //如果为最后一个匹配规则,结束循环，返回匹配标识数
                    flag = true;       //结束标志位为true
                    resultMatcherFlag = matchFlag;
                    if (minMatchTYpe == matchType) { //最小规则，直接返回,最大规则还需继续查找
                        break;
                    }
                }
            } else {     //不存在，直接返回
                break;
            }
        }
        if (matchFlag < 1 || !flag) {//长度必须大于等于1，为词
            matchFlag = 0;
        }
        return resultMatcherFlag;
    }
    private static final String IS_END = "isEnd";
    private static class DFANode extends HashMap{
        public DFANode(boolean root){
            if(!root) {
                this.put(IS_END,0);
            }
        }
        private DFANode setEnd(boolean isEnd){
            this.put(IS_END,isEnd?1:0);
            return this;
        }
        private boolean isEndNode(){
            return this.get(IS_END)!=null&&1==(Integer) (this.get(IS_END))?true:false;
        }
        private DFANode setChild(String data,DFANode dfaNode){
            this.put(data,dfaNode);
            return this;
        }
        private DFANode getChild(String data){
            return (DFANode) this.get(data);
        }
    }
    public static void main(String[] args) {
        AdvanceDFA dfa = new AdvanceDFA();
        dfa.build("秋意浓");
        dfa.build("秋风起时");
        dfa.build("风吹云散");
        dfa.build("云淡风轻");
        System.out.println(dfa);
    }
}
