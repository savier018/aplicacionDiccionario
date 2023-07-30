/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.aplicaciondiccionario;

import Modelo.Palabra;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author User
 */
public class SignificadoController implements Initializable {

    @FXML
    private Label labelPalabra;
    
    @FXML
    private Label labelSignificado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void mostrarPalabra(Palabra palabraSeleccionada) {
        labelPalabra.setText(palabraSeleccionada.getTermino());
        labelSignificado.setText(palabraSeleccionada.getDefinicion());
    }
    
}
