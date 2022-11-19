package net.alkitmessenger.client.controllers.authorize;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.alkitmessenger.client.AlkitMessengerClient;
import net.alkitmessenger.packet.packets.output.UserLoginPacket;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginController implements Initializable {

    final AlkitMessengerClient alkitMessengerClient = AlkitMessengerClient.getAlkitMessengerClient();

    @FXML
    TextField mailField;
    @FXML
    PasswordField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }

    @FXML
    private void onAuthorizeClick() {

        String mail = mailField.getText();
        String password = passwordField.getText();

        Pattern mailPattern = Pattern.compile("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$");
        Matcher mailMatcher = mailPattern.matcher(mail);

        Pattern passwordPattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{8,}");
        Matcher passwordMatcher = passwordPattern.matcher(mail);

        if (!mailMatcher.matches() || !passwordMatcher.matches())
            return;

        if (password.length() == 0)
            return;

        alkitMessengerClient.getServerConnection().addPacket(new UserLoginPacket(password));

    }

    @FXML
    private void onRegistrationClick() {



    }
}