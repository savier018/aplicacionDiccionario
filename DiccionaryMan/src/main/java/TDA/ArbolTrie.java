package TDA;

import com.mycompany.diccionaryman.Palabra;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ArbolTrie {
    private TrieNode root;

    public ArbolTrie() {
        this.root = new TrieNode();
    }
    
    public boolean isEmpty(){
        return this.root.getChildren().isEmpty();
    }   
    
    public void delete(String word) {
    if (word == null || word.isEmpty()) {
        throw new IllegalArgumentException("La palabra no puede ser nula o vacía");
    }    
        boolean deletedFromTrie = deleteFromTrie(root, word, 0);
        if (!deletedFromTrie) {
            updateTextFile(word);
        }
    }

    private boolean deleteFromTrie(TrieNode node, String word, int profundidadPalabra) {
        if (node == null) {
            return false;
        }

        if (profundidadPalabra == word.length()) {
            if (!node.isEndOfWord()) {   
                return false;
            }

            node.setIsEndOfWord(false);
            
            return node.getChildren().isEmpty();
        }

        char caracter = word.charAt(profundidadPalabra);
        TrieNode nodoHijo = node.getChildren().get(caracter);

        if (nodoHijo == null) {
            return false;
        }

        boolean eliminarNodo = deleteFromTrie(nodoHijo, word, profundidadPalabra + 1);

        if (eliminarNodo) {
            node.getChildren().remove(caracter);
            return !node.isEndOfWord() && node.getChildren().isEmpty();
        }

        return false;
    }
    
    private void updateTextFile(String wordToDelete) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\User\\Documents\\aplicacionDiccionario\\DiccionaryMan\\src\\main\\resources\\text\\palabras.txt"), StandardCharsets.UTF_8);

            List<String> newLines = lines.stream()
                .filter(line -> !line.startsWith(wordToDelete + "-"))
                .collect(Collectors.toList());

            Files.write(Paths.get("C:\\Users\\User\\Documents\\aplicacionDiccionario\\DiccionaryMan\\src\\main\\resources\\text\\palabras.txt"), newLines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            Palabra palabraEncontrada = new Palabra(palabraActual, node.getContent());
            result.add(palabraEncontrada);
        }
        for (Map.Entry<Character, TrieNode> entry : node.getChildren().entrySet()) {
            collectWordsWithPrefix(entry.getValue(), palabraActual + entry.getKey(), result);
        }
    }
    
    public boolean clearWord(String word){
    if (root == null){ 
        return false;
    }
    else{
    TrieNode current = root;
    
    
    return true;
    }
    }
    
    public int getHeight(){          
    return getHeightR(root);
    }
    
    private int getHeightR(TrieNode current){ 
      if(current==null){ 
        return 0;}
      else{    
        int i=1;  
        for(Map.Entry<Character,TrieNode> entry : current.getChildren().entrySet()){
        i = Math.max(getHeightR(entry.getValue()),i);
        }
        return i;
        }
          
    
    }
    
    
    public int countWordsinTrie() {
    return countLeaves(root);
  }
    
    
    private int countLeaves(TrieNode root){
        if(root==null){ 
        return 0;}
        TrieNode current = root;
        if(root.isEndOfWord()){
        return 1;
        }
        else{
        int counter=0;
        for(Map.Entry<Character,TrieNode> entry : current.getChildren().entrySet()){
        counter += countLeaves(entry.getValue());
        }
        return counter;
        }
    }
}
