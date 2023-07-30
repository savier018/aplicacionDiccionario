
package com.mycompany.aplicaciondiccionario;

import Modelo.Usuario;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class RegistroController implements Initializable {

    @FXML
    private PasswordField textContraseñaRegistro;
    @FXML
    private TextField textUsuarioRegistro;
    
    private static ArrayList<Usuario> listaUsuarios = Usuario.cargarUsuarios();
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
 
    @FXML
    public void registrarse(ActionEvent event) throws IOException {
        String username = textUsuarioRegistro.getText();
        String password = textContraseñaRegistro.getText();
        Usuario user = new Usuario(username, password);
        listaUsuarios.add(user);
        Usuario.sobreescribirFichero(listaUsuarios);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Usuario creado exitosamente");
        alert.setContentText("Su usuario ha sido creado exitosamente");
        alert.showAndWait();
        alert.close();
        App.setRoot("pantallaUsuario");
        
    }

    @FXML
    public void volver(ActionEvent event) throws IOException {
        try {
            App.setRoot("primary");
        } catch(IOException e){
            System.out.println(e);
        }
    }
 
}
