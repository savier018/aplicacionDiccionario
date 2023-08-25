/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.diccionaryman;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author Win1OPro Station
 */
public class WordGame implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private int lives = 3;
    private String wordtoguess;
    private String guess;
    private static ArrayList<String> WordList = new ArrayList();
    private ArrayList<Integer> SelectedNumberList = new ArrayList();
    @FXML
    private AnchorPane GameArea;
    @FXML
    private TextField GuessInput;
    @FXML
    private HBox WordContainer;
    @FXML
    private Button Confirm,EndGame;
    @FXML
    private Label Timer,TimerLegend,Lives,LegendText,Title;
    private Timer GameTimer;
    private long min, sec, hr,TotalSec=0;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupGame();
        TimerStart();
    }
   
    
            
    public void convertTime(){
        min = TimeUnit.SECONDS.toMinutes(TotalSec);
        sec = TotalSec - (min * 60);
        hr = TimeUnit.MINUTES.toHours(min);
        min = min - (hr * 60);
        Timer.setText(hr + ":" + min + ":" + sec);

        TotalSec--;
    }        
    
   @FXML 
   private void TimerStart(){
    TotalSec=120;
    this.GameTimer= new Timer();
    TimerTask timerHandler = new TimerTask(){
    @Override
    public void run(){
        Platform.runLater(new Runnable(){
        @Override
        public void run(){
        convertTime();
        if(TotalSec<=0){
        GameTimer.cancel();
        Timer.setText("00:00:00");
            try {
                EndGame();
            } catch (IOException ex) {
                System.out.println(ex);
            }
}}}
);};};
    GameTimer.schedule(timerHandler,0,1000);   
            }
   
    
  public static void setTimeout(Runnable runnable, int delay){
          
          new Thread(()->{
          try{
              Thread.sleep(delay);
              runnable.run();
          }
          catch(Exception e){
          System.err.println(e);}
      }).start();
          
      } 
    
    
    
    
    
    //  SetupMethods
    private void setupGame(){
    ArrayList<Palabra> lp = Palabra.cargarPalabras();
    for(Palabra p:lp){
            WordList.add(p.getTermino());
        }  
    getRandomWordfromTrie();
    getSetupHBox();
    }
    
    private void getRandomWordfromTrie(){
    Random rd = new Random();
    int int_random = rd.nextInt(WordList.size());
    wordtoguess = WordList.get(int_random);
    }
    
    private void getSetupHBox(){
    Random rd = new Random();   
    int id = rd.nextInt(wordtoguess.length());
        System.out.println(id);
    for (int i = 0; i < wordtoguess.length(); i++) {
    Rectangle rect = new Rectangle(30,30); 
    rect.setFill(Color.BLUE);    
    HBox.setMargin(rect, new Insets(1));
    HBox.setHgrow(rect, Priority.ALWAYS);
    WordContainer.getChildren().add(rect);
    }
    Label L= new Label();
    L.setText(wordtoguess.substring(id, id+1));
    L.setStyle("-fx-background-color: #2a135b;-fx-display: flex;\n -fx-align-content: center;-fx-text-align: center; -fx-text-fill: white;-fx-font-family: 'Lucida Console', cursive; -fx-font-size:18;");
    SelectedNumberList.add(id);
    WordContainer.getChildren().set(id,L);
    }
    
    @FXML
    private void confirmAnswer() throws IOException{
    String l= GuessInput.getText();
    if(l.equals(wordtoguess)){
        WIN();
    }
    else{
    lives--;
    Lives.setText(lives+"");
    if(lives<=0){
    EndGame();
    }
    addRandomLetterToBox();
    }
    
    }
    
    private void addRandomLetterToBox(){
    boolean gotDN = true;
    Random rd = new Random();
    int id = 0;
    while(gotDN){
     id= rd.nextInt(wordtoguess.length());
     System.out.println(id);
     if(!SelectedNumberList.contains(id)){
     gotDN=false;
     SelectedNumberList.add(id); 
     }
    } 
    Label L= new Label();
    L.setText(wordtoguess.substring(id, id+1));
    L.setStyle("-fx-background-color: #2a135b;-fx-display: flex;\n -fx-align-content: center;-fx-text-align: center; -fx-text-fill: white;-fx-font-family: 'Lucida Console', cursive; -fx-font-size:18;");
    
    WordContainer.getChildren().set(id,L);
    }
    
    
    private void WIN() throws IOException{
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Winner Winner Chicken Dinner!");
    GameTimer.cancel();
    int Score = lives*(int)TotalSec*wordtoguess.length();  
    alert.setContentText("Congratulations, You Won. Your Score is" + Score);
    if(alert.showAndWait().get() == ButtonType.OK){
        alert.close();
        switchToSecondary();
    }
    }
    
    @FXML
    private void EndGame() throws IOException{
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Game End....");
    alert.setContentText("Well....You Tried....Better Luck Next Time!");
    if(alert.showAndWait().get() == ButtonType.OK){
        alert.close();
        switchToSecondary();
    }
    }
    
    @FXML
    private void switchToSecondary() throws IOException {   
       App.setRoot("secondary");
    }
    
}
