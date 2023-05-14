module com.example.sena {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.sena to javafx.fxml;
    exports com.example.sena;
}