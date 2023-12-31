package com.mycompany.diccionaryman;

import TDA.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.FileChooser;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

public class SecondaryController implements Initializable {
    @FXML
    private AnchorPane SCpane;
    @FXML
    private Label Title;
    @FXML
    private Label DiccionaryTitle;
    
    private static String DiccionaryString;
    private String WritableStats;
    
    @FXML
    private VBox TextFieldContainer;
    @FXML
    private GridPane ButtonMenu;
    @FXML
    private Label LegendText,MeaningText,Mode; 
    @FXML
    private TextField TextInput, MeaningInput;
    @FXML
    private Button Add,Delete,Search,Save,Stats,Game,Confirm,SearchMeaning, AddtoList,Download;        
    
    private static ArrayList<Palabra> listaPalabras = new ArrayList();
    private static Map<String,String> SigP = new HashMap();
    private static ArbolTrie Tree;    
    private AutoCompletionBinding auto;
    private AudioClip song;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DiccionaryTitle.setText(DiccionaryString);
        if(DiccionaryString.equals("palabras")){
            loadTree();    
        } else{
            loadTree(DiccionaryString);
        }
        playSongC();
    }
    
    private void loadTree(String texto){
        listaPalabras = Palabra.cargarPalabras(texto);
        Tree= new ArbolTrie();
        for(Palabra p:listaPalabras){
            Tree.insert(p.getTermino());
            SigP.put(p.getTermino(),p.getDefinicion());
        } 
    }
    private void loadTree(){
        listaPalabras = Palabra.cargarPalabras();
        Tree= new ArbolTrie();
        for(Palabra p:listaPalabras){
            Tree.insert(p.getTermino());
            SigP.put(p.getTermino(),p.getDefinicion());
        }  
    }
    
     public void playSongC(){ 
        Platform.runLater(new Runnable(){
        @Override
        public void run(){
        song = new AudioClip(getClass().getResource("/Music/MainMenuTheme.mp3").toExternalForm());   
        song.setCycleCount(AudioClip.INDEFINITE);
        song.play(); 
                }
       });
       }
    
    private ArrayList<String> getStats(){
    ArrayList<String> allStats = new ArrayList();
    int l= Tree.countWordsinTrie();
    int li= Tree.getHeight();
    allStats.add("Words in the Trie: " +l);
    allStats.add("Height of Trie: " + li);
    return allStats;
    }
    
    
    private void updateObservableList(){
        ArrayList<Palabra> lp= new ArrayList();
        String f =TextInput.getText();
        System.out.println(f);
        Tree.collectWordsWithPrefix(Tree.getTrieNodeWithPrefix(f), f,lp);
        for(Palabra p:lp){
            System.out.println(p.getTermino()+""+p.getDefinicion());
        } 
        listaPalabras=lp;
        auto.dispose();
        auto = TextFields.bindAutoCompletion(TextInput, listaPalabras);
        System.out.println(listaPalabras.toString());
    }
    
    //Button Commands;
    @FXML
    private void changeModeSearch(){
        if(auto!=null){
        auto.dispose();
        }
        Mode.setText("Mode: Search");
        TextFieldContainer.getChildren().clear();
        SearchMeaning = new Button("Get Meaning");
        SearchMeaning.setStyle("-fx-background-color: #2a135b;-fx-display: flex;\n -fx-align-content: center;-fx-text-align: center; -fx-text-fill: white;-fx-font-family: 'Lucida Console', cursive;");
        SearchMeaning.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            getMeaning();
        }
    });
        TextFieldContainer.getChildren().addAll(LegendText,TextInput,SearchMeaning);
        auto = TextFields.bindAutoCompletion(TextInput, listaPalabras);
        TextInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent t) {
            updateObservableList();
        }
    });
    
    
    }
    
//    @FXML
//    private void changeModeDelete(){
//        if(auto!=null){
//        auto.dispose();
//        }
//        Mode.setText("Mode: Delete");
//        TextFieldContainer.getChildren().clear();
//        Confirm = new Button("Confirm");
//        Confirm.setOnMouseClicked(new EventHandler<MouseEvent>() {
//        @Override
//        public void handle(MouseEvent t) {
//            DeleteWord();
//        }
//    });
//        TextFieldContainer.getChildren().addAll(LegendText,TextInput,Confirm);
//        auto = TextFields.bindAutoCompletion(TextInput, listaPalabras);
//        TextInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
//        @Override
//        public void handle(KeyEvent t) {
//            updateObservableList();
//        }
//    });
//    }
    
    @FXML
    private void changeModeDelete(){
        if(auto!=null){
        auto.dispose();
        }
        Mode.setText("Mode: Delete");
        TextFieldContainer.getChildren().clear();
        Confirm = new Button("Confirm");
        Confirm.setStyle("-fx-background-color: #2a135b;-fx-display: flex;\n -fx-align-content: center;-fx-text-align: center; -fx-text-fill: white;-fx-font-family: 'Lucida Console', cursive;");
        TextFieldContainer.getChildren().addAll(LegendText,TextInput,Confirm);
        auto = TextFields.bindAutoCompletion(TextInput, listaPalabras);
        TextInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent t) {
            updateObservableList();
        }
        });
        
        Confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleDelete(); 
            }
        });
    }
      
    @FXML
    private void handleDelete() {
        if (Tree == null) {
            return;
        }

        String palabraAEliminar = TextInput.getText();
        if (Tree.search(palabraAEliminar)) {
            Tree.delete(palabraAEliminar);
            TextInput.clear();
        } else {
            System.out.println("La palabra no existe en el diccionario.");
        }
    }

    
    @FXML
    private void changeModeAdd(){
        if(auto!=null){
        auto.dispose();
        }
        Mode.setText("Mode: Add");
        TextFieldContainer.getChildren().clear();
        AddtoList = new Button("Add to List");
        AddtoList.setStyle("-fx-background-color: #2a135b;-fx-display: flex;\n -fx-align-content: center;-fx-text-align: center; -fx-text-fill: white;-fx-font-family: 'Lucida Console', cursive;");
        AddtoList.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            addWord();
        }
    });
        TextFieldContainer.getChildren().addAll(LegendText,TextInput,MeaningText,MeaningInput,AddtoList);
        auto = TextFields.bindAutoCompletion(TextInput, listaPalabras);
        TextInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent t) {
            updateObservableList();
        }
    });
    }
    
    @FXML
    private void downloadLibrary() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Desea descargar el archivo?");
        if(alert.showAndWait().get() == ButtonType.OK){
            File file = new File(String.format("src/main/resources/text/%s.txt",DiccionaryTitle.getText()));
            String home = System.getProperty("user.home");
            File newfile = new File(home+"\\Downloads\\"+DiccionaryString+".txt");
            try{
                Files.copy(file.toPath(),newfile.toPath(),StandardCopyOption.REPLACE_EXISTING);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Archivo descargado");
                alert2.setContentText("Se descargo su archivo en la parte de descargas!");
                alert2.showAndWait();
            }catch(IOException e){
                e.printStackTrace();
            }
            
        }    
        alert.close();
        
    }
    
    
    
    @FXML
    private void giveStats(){
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Stats!");
    ArrayList<String> l= getStats();
    String f="";
    for(String i:l){
    f=f+"\n"+i;
    }
    alert.setContentText(f);
    if(alert.showAndWait().get() == ButtonType.OK){
        alert.close();
        }      
    }
    
    @FXML
    private void changeModeGame()throws IOException{
        Game.getScene().getWindow().setWidth(600);
        Game.getScene().getWindow().setHeight(400);
        App.setRoot("Game");
        song.stop();
    }
    

    
    @FXML
    private void addWord(){
    String f= TextInput.getText();   
    if(!Tree.search(f)){
    Tree.insert(f);
    String fM= MeaningInput.getText();
    SigP.put(f, fM);
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Sucess");
    alert.setContentText("Word Added");
    if(alert.showAndWait().get() == ButtonType.OK){
        alert.close();
        } 
    }
    else{
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Already in Trie");
    alert.setContentText("The Word You tried to add was already in the Trie");
    if(alert.showAndWait().get() == ButtonType.OK){
        alert.close();
        }      
    }
    }
    
    @FXML
    private void DeleteWord(){
    String f= TextInput.getText();
    if(Tree.search(f)){
    //Trie.clear(f);
    SigP.remove(f);
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Delete!");
    alert.setContentText("Word has been deleted succesfully");
    if(alert.showAndWait().get() == ButtonType.OK){
        alert.close();
        }      
    }
    else{
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Not Found!");
    alert.setContentText("The Word You tried to search is not in the Trie");
    if(alert.showAndWait().get() == ButtonType.OK){
        alert.close();
        } 
    }
    }
    
    @FXML
    private void getMeaning(){
    String f= TextInput.getText();   
    if(Tree.search(f)){
    String fs = SigP.get(f);
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Meaning!");
    alert.setContentText(fs);
    if(alert.showAndWait().get() == ButtonType.OK){
        alert.close();
        }      
    }
    else{
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Not Found!");
    alert.setContentText("The Word You tried to search is not in the Trie");
    if(alert.showAndWait().get() == ButtonType.OK){
        alert.close();
        }  
    
    }
    }
    
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    
    public static void setDiccionaryTitle(String title){
        DiccionaryString = title;
    }
    
    //JUEGO ADIVINAR PALABRA DEL DICCIONARIO
    //Agarro una palabra del arbol y le digo al usuario que la adivine.
    //le doy una letra al azar para que se guie
    // 3 intentos.

    
    
    
    
}