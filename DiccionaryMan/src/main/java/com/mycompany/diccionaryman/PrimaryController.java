package com.mycompany.diccionaryman;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PrimaryController implements Initializable{
    
    @FXML
    private Label Title;
    @FXML
    private Button InsertButton;
    @FXML
    private Button seguirbtn;
    @FXML
    private Button defaultbtn;
   
    File selectedFile;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
    @FXML
    private void returndefault(){
        Title.setText("palabras");
    }
    
    @FXML
    private void insertarTxt()  {
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Open a file");
        filechooser.setInitialDirectory(new File("C:\\"));
        filechooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TEXT files","*.txt"),new FileChooser.ExtensionFilter("All files", "*.txt"));
        Stage stage = (Stage) InsertButton.getScene().getWindow();
        selectedFile = filechooser.showOpenDialog(stage);
        if(selectedFile!= null){
            Title.setText(selectedFile.getName());
            registrarTxt();  
        } else{
            System.out.println("No file has been selected");
            }
    }
    
    
    @FXML
    private void switchToSecondary() throws IOException {
        if(!Title.getText().equals("palabras")){
            SecondaryController.setDiccionaryTitle(Title.getText());
            //Cambiar para el diccionario nuevo
        } else{
            SecondaryController.setDiccionaryTitle("palabras");
        }
        seguirbtn.getScene().getWindow().setWidth(400);
        seguirbtn.getScene().getWindow().setHeight(350);
        
        App.setRoot("secondary");
    }
    
     public void registrarTxt(){
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(String.format("src/main/resources/text/%s",Title.getText())));
            BufferedReader br = new BufferedReader(new FileReader(selectedFile.getAbsoluteFile()));
            bw.write(br.readLine());
            String line;
            while((line = br.readLine()) != null){
                bw.write(line);
            }   
            bw.close();
            br.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Archivo guardado");
            alert.setContentText("Su archivo de texto a sido creado en el repositorio");
            alert.showAndWait();
            alert.close();
        }catch (IOException e){
            System.out.println("error");
        }
    }
     
    
    
}
