package TDA;

import com.mycompany.diccionaryman.Palabra;
import java.util.ArrayList;
import java.util.Map;

public class ArbolTrie {
    private TrieNode root;

    public ArbolTrie() {
        this.root = new TrieNode();
    }
    
    public boolean isEmpty(){
        return this.root.getChildren().isEmpty();
    }   
    
    public void insert(String word) {
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("La palabra no puede ser nula o vacía");
        }
        
        TrieNode actual = root;
        for (Character ch : word.toCharArray()) {
            actual = actual.getChildren().computeIfAbsent(ch, c -> new TrieNode());
            actual.setContent(actual.getContent() + ch);
        }
        actual.setIsEndOfWord(true);
    }
    
    public boolean search(String word) {
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("La palabra no puede ser nula o vacía");
        }
        
        TrieNode current = root;
        for (Character ch : word.toCharArray()) {
            current = current.getChildren().get(ch);
            if (current == null) {
                return false;
            }
        }
        return current.isEndOfWord();
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
    
    public void collectWordsWithPrefix(TrieNode node, String palabraActual, ArrayList<Palabra> result) {
        if (node.isEndOfWord()) {
            Palabra palabraEncontrada = new Palabra(palabraActual, "");
            result.add(palabraEncontrada);
        }
        for (Map.Entry<Character, TrieNode> entry : node.getChildren().entrySet()) {
            collectWordsWithPrefix(entry.getValue(), palabraActual + entry.getKey(), result);
        }
    }
  
}
