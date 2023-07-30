
package com.mycompany.aplicaciondiccionario;

import Modelo.Palabra;
import TDA.ArbolTrie;
import TDA.TrieNode;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;


public class PantallaUsuarioController implements Initializable {
    
    private static ArrayList<Palabra> listaPalabras = Palabra.cargarPalabras();
    private static Map<Character, ArrayList<Palabra>> mapPalabras = Palabra.sublistasPorLetra(listaPalabras);
    private static ArbolTrie tree;
    
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ComboBox<Palabra> comoBoxBuscador;
    @FXML
    private ListView<Palabra> listaPalabras1;
    @FXML
    private ListView<Palabra> listaPalabras2;
    @FXML
    private ListView<Palabra> listaPalabras3;

    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        ArrayList<Palabra> listaA = mapPalabras.get('A');
        ArrayList<Palabra> listaB = mapPalabras.get('B');
        ArrayList<Palabra> listaC = mapPalabras.get('C');
        configurarListView(listaPalabras1);
        configurarListView(listaPalabras2);
        configurarListView(listaPalabras3);    
        listaPalabras1.setItems(FXCollections.observableArrayList(listaA));
        listaPalabras2.setItems(FXCollections.observableArrayList(listaB));
        listaPalabras3.setItems(FXCollections.observableArrayList(listaC));
        tree = new ArbolTrie();
        for (Palabra palabra : listaPalabras) {
            tree.insert(palabra.getTermino());
        }
        
        comoBoxBuscador.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            buscarPalabrasSimilares(newValue);
            });
        
    }
       
    public void configurarListView(ListView<Palabra> listView) {
        listView.setCellFactory((ListView<Palabra> param) -> new ListCell<>() {
            @Override
            protected void updateItem(Palabra item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getTermino());
                }
            }
        });
        
        listView.setOnMouseClicked(event -> {
            if (!listView.getSelectionModel().isEmpty()) {
                Palabra palabraSeleccionada = listView.getSelectionModel().getSelectedItem();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Significado.fxml"));
                    Parent significadoParent = loader.load();
                    SignificadoController significadoController = loader.getController();

                    significadoController.mostrarPalabra(palabraSeleccionada);

                    Scene significadoScene = new Scene(significadoParent);
                    Stage nuevaVentana = new Stage();
                    nuevaVentana.setScene(significadoScene);
                    nuevaVentana.show();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        });
    }
    
    private void buscarPalabrasSimilares(String prefix) {
        ArrayList<Palabra> palabrasSimilares = new ArrayList<>();
        TrieNode current = tree.getTrieNodeWithPrefix(prefix);
        if (current != null) {
            tree.collectWordsWithPrefix(current, prefix, palabrasSimilares);
        }

        comoBoxBuscador.getItems().clear();
        comoBoxBuscador.getItems().addAll(palabrasSimilares);
        comoBoxBuscador.show();
    }


}
