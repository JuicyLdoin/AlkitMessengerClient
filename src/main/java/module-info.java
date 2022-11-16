module com.example.alkitmessengerclient {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.alkitmessengerclient to javafx.fxml;
    exports com.example.alkitmessengerclient;
}