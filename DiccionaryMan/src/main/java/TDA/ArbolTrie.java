package TDA;

import com.mycompany.diccionaryman.Palabra;
import java.util.ArrayList;

public class ArbolTrie {
    private TrieNode root;

    public ArbolTrie() {
        this.root = new TrieNode();
    }
    
    public boolean isEmpty(){
        return this.root.getChildren().isEmpty();
    }
    
    public void insert(String word){
        if (word == null || word.isEmpty()){
            throw new UnsupportedOperationException("No se ha ingresado ninguna palabra");
        } else if (this.root == null){
            this.root = new TrieNode();
        } else {
            TrieNode current = this.root;
            for(Character ch : word.toCharArray()){
                current = current.getChildren().computeIfAbsent(ch, c -> new TrieNode());
                current.setContent(current.getContent() + ch);
            }
            current.setIsEndOfWord(true);
        }
    }
    
    public boolean search(String word){
        if (word == null || word.isEmpty()){
            throw new UnsupportedOperationException("No se ha ingresado ninguna palabra");
        } else if (this.root == null) {
            this.root = new TrieNode();
        } else {
            TrieNode current = this.root;
            for (Character ch : word.toCharArray()){
                current = current.getChildren().get(ch);
                if (current == null){
                    return false;
                }
            }
            return current.isEndOfWord();
        }
        return false;
    }
    
    public TrieNode getTrieNodeWithPrefix(String prefix) {
        TrieNode current = root;
        for (Character ch : prefix.toCharArray()) {
            current = current.getChildren().get(ch);
            if (current == null) {
                return null;
            }
        }
        return current;
    }
    
    public void collectWordsWithPrefix(TrieNode node, String prefix, ArrayList<Palabra> result) {
        if (node.isEndOfWord()) {
            Palabra palabraEncontrada = new Palabra(prefix, "");
            result.add(palabraEncontrada);
        }
        for (TrieNode child : node.getChildren().values()) {
            collectWordsWithPrefix(child, prefix + child.getContent(), result);
        }
    }
    
}
