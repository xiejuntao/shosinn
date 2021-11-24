package xjt.algo.trie;

import lombok.ToString;
import java.util.HashSet;
import java.util.Set;

/**
 *应用于搜索关键
 * */
@ToString
public class Trie {
    private TrieNode rootNode = new TrieNode('/');
    public Trie(){

    }
    public Trie(Set<String> lines){
        for (String line:lines) {
            build(line);
        }
    }
    public Trie build(String line){
        TrieNode p = rootNode;
        for (int i=0;i<line.length();i++){
            char c = line.charAt(i);
            TrieNode trieNode = p.getChildNodes().get(c);
            if(trieNode==null){
                trieNode = new TrieNode(c);
                p.getChildNodes().put(c,trieNode);
            }
            p = trieNode;
        }
        p.setEndNode(true);
        return this;
    }
    public boolean find(String line,boolean fullMatch){
        TrieNode p = rootNode;
        for (int i=0;i<line.length();i++){
            char c = line.charAt(i);
            TrieNode trieNode = p.getChildNodes().get(c);
            if(trieNode==null){
                return false;
            }
            p = trieNode;
        }
        if(!p.isEndNode() & fullMatch){
            return false;
        }else {
            return true;
        }
    }
    public static int minMatchTYpe = 1;      //最小匹配规则
    public static int maxMatchType = 2;      //最大匹配规则
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
    public int checkMatchWord(String txt, int beginIndex,int matchType) {
        boolean flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
        int matchFlag = 0;     //匹配标识数默认为0
        int resultMatcherFlag = 0;     //匹配标识数默认为0
        char word = 0;
        TrieNode p = rootNode;
        for (int i = beginIndex; i < txt.length(); i++) {
            word = txt.charAt(i);
            p = p.getChildNodes().get(word);//获取指定key
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
}
