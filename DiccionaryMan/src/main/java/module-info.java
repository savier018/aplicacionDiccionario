module com.mycompany.diccionaryman {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;

    opens com.mycompany.diccionaryman to javafx.fxml;
    exports com.mycompany.diccionaryman;
}
