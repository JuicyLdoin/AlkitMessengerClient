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
import net.alkitmessenger.packet.packets.input.UserDataPacket;
import net.alkitmessenger.packet.packets.output.UserLoginPacket;
import net.alkitmessenger.util.builder.PacketFeedbackBuilder;

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
    private void onAuthorizeClick() throws InterruptedException, IOException {

        String mail = mailField.getText();
        String password = passwordField.getText();

        if (mail.isEmpty()) {

            infoLabel.setText("Введите электронную почту!");
            return;

        }

        if (password.isEmpty()) {

            infoLabel.setText("Введите пароль!");
            return;

        }

        Pattern mailPattern = Pattern.compile("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$");
        Matcher mailMatcher = mailPattern.matcher(mail);

//        Pattern passwordPattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{8,}");
//        Matcher passwordMatcher = passwordPattern.matcher(mail);

        if (!mailMatcher.matches())
            return;

        AtomicBoolean login = new AtomicBoolean(false);

        PacketFeedback packetFeedback = new PacketFeedbackBuilder()
                .setWaitThread(Thread.currentThread())
                .addConsumer(PacketFeedback.Reason.EXCEPTION, feedback -> {

                    if (feedback.getException().equals("User not found")) {

                        login.set(false);
                        infoLabel.setText("Пользователь не найден!");

                        feedback.setRead(true);

                    }
                })
                .addConsumer(PacketFeedback.Reason.EXCEPTION, feedback -> {

                    if (feedback.getException().equals("Password not equal")) {

                        login.set(false);
                        infoLabel.setText("Пароль не верный!");

                        feedback.setRead(true);

                    }
                })
                .addConsumer(PacketFeedback.Reason.PACKET, feedback -> {

                    if (feedback.getReceivedPacket() instanceof UserDataPacket) {

                        login.set(true);

                        feedback.setRead(true);

                    }
                })
                .build();

        serverConnection.addPacketFeedBack(packetFeedback);
        serverConnection.addPacket(new UserLoginPacket(password));

        synchronized (packetFeedback) {

            packetFeedback.wait();

        }

        if (login.get())
            Windows.MAIN.open();

    }

    @FXML
    private void onRegistrationClick() throws IOException {

        Windows.REGISTER.open();

    }
}