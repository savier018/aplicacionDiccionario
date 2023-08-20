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
