package net.alkitmessenger.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private ImageView user_avatar;
    @FXML
    private Label user_name_label;

    @FXML
    private GridPane friends_list;
    @FXML
    private VBox users_list;

    @FXML
    private TextField friends_find_field;
    @FXML
    private TextField user_find_field;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void onFriendFindClick() {



    }

    @FXML
    private void onUserFindClick() {



    }
}