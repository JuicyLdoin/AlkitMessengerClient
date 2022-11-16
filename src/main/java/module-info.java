module net.alkitmessenger {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;

    requires com.google.gson;

    requires static lombok;
    requires org.jetbrains.annotations;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens net.alkitmessenger.client.controllers to javafx.fxml, javafx.web, lombok, com.google.gson;
    opens net.alkitmessenger.client to javafx.fxml, javafx.graphics, javafx.web, lombok, com.google.gson;
    opens net.alkitmessenger.util to lombok, com.google.gson;

}