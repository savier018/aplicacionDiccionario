module com.mycompany.aplicaciondiccionario {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.aplicaciondiccionario to javafx.fxml;
    exports com.mycompany.aplicaciondiccionario;
}
