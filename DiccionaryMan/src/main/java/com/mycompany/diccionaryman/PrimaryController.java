package com.mycompany.diccionaryman;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class PrimaryController {
    @FXML
    private AnchorPane PCpane;
    @FXML
    private Label Title;
    @FXML
    private Button InsertButton;
    

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
