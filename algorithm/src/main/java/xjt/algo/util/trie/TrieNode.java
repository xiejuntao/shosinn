package xjt.algo.util.trie;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class TrieNode {
    private char data;
    private Map<Character,TrieNode> childNodes = new HashMap<>();
    private boolean isEndNode = false;
    public TrieNode(char data){
        this.data = data;
    }
}
