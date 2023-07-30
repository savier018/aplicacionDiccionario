package com.mycompany.aplicaciondiccionario;

import Modelo.Usuario;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class PrimaryController implements Initializable {

    @FXML
    private TextField textUsuario;
    @FXML
    private PasswordField textContraseña;

    private static ArrayList<Usuario> listaUsuarios = Usuario.cargarUsuarios();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void iniciarSesion(ActionEvent event) throws IOException{
        int contador = 0;
        String nombreUsuario = textUsuario.getText();
        String contraseña = textContraseña.getText();
        for (int i = 0; i < listaUsuarios.size(); i++) {
            Usuario user = listaUsuarios.get(i);
            if (nombreUsuario.equals(user.getUsername()) && contraseña.equals(user.getPassword())){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("PantallaUsuario.fxml"));
                Parent pantallaUsuarioParent = loader.load();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setWidth(800); 
                stage.setHeight(600); 

                Scene pantallaUsuarioScene = new Scene(pantallaUsuarioParent);
                stage.setScene(pantallaUsuarioScene);
                stage.show();
                contador++;
            } 
            
        }
       
        if (contador == 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al iniciar sesión");
                alert.setContentText("El usuario o contraseña ingresados son incorrectos, inténtelo de nuevo");
                alert.showAndWait();
                App.setRoot("primary");
                alert.close();
            }
        
    }

    @FXML
    public void registrar(ActionEvent event) throws IOException{
        try {
            App.setRoot("Registro");
        } catch (IOException e){
            System.out.println(e);
        }
    }
    
}
