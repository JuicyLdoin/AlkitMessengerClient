package net.alkitmessenger.client.controllers.authorize;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.alkitmessenger.client.AlkitMessengerClient;
import net.alkitmessenger.client.ServerConnection;
import net.alkitmessenger.client.Windows;
import net.alkitmessenger.packet.PacketFeedback;
import net.alkitmessenger.packet.Packets;
import net.alkitmessenger.packet.packets.output.UserLoginPacket;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginController implements Initializable {

    final AlkitMessengerClient alkitMessengerClient = AlkitMessengerClient.getAlkitMessengerClient();
    final ServerConnection serverConnection = alkitMessengerClient.getServerConnection();

    @FXML
    Label infoLabel;

    @FXML
    TextField mailField;
    @FXML
    PasswordField passwordField;

    @FXML
    CheckBox rememberMeBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }

    @FXML
    private void onAuthorizeClick() throws InterruptedException {

        String mail = mailField.getText();
        String password = passwordField.getText();

        Pattern mailPattern = Pattern.compile("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$");
        Matcher mailMatcher = mailPattern.matcher(mail);

        Pattern passwordPattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{8,}");
        Matcher passwordMatcher = passwordPattern.matcher(mail);

        if (password.length() == 0)
            return;

        if (!mailMatcher.matches() || !passwordMatcher.matches())
            return;

        AtomicBoolean login = new AtomicBoolean(false);

        serverConnection.addPacketFeedBack(new PacketFeedback(Thread.currentThread(),
                Packets.USER_DATA_PACKET, "User not found", reason -> {

            if (reason.getReason().equals(PacketFeedback.Reason.PACKET))
                login.set(true);

        }));

        serverConnection.addPacketFeedBack(new PacketFeedback(Thread.currentThread(),
                Packets.USER_DATA_PACKET, "Password not equal", reason -> {

            if (reason.getReason().equals(PacketFeedback.Reason.PACKET))
                login.set(true);

        }));

        serverConnection.addPacket(new UserLoginPacket(password));

        wait();

        if (!login.get()) {

        }
    }

    @FXML
    private void onRegistrationClick() throws IOException {

        Windows.REGISTER.open();

    }
}