package xjt.algo.trie;

import lombok.ToString;
/**
 *应用于搜索关键
 * */
@ToString
public class Trie {
    private TrieNode rootNode = new TrieNode('/');
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
}
