package net.alkitmessenger.client.controllers.authorize;

import javafx.fxml.FXML;
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
import net.alkitmessenger.packet.packets.ExceptionPacket;
import net.alkitmessenger.packet.packets.input.UserDataPacket;
import net.alkitmessenger.packet.packets.output.UserRegistrationPacket;
import net.alkitmessenger.util.builder.PacketFeedbackBuilder;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationController {

    final AlkitMessengerClient alkitMessengerClient = AlkitMessengerClient.getAlkitMessengerClient();
    final ServerConnection serverConnection = alkitMessengerClient.getServerConnection();

    @FXML
    Label infoLabel;

    @FXML
    TextField nameField;
    @FXML
    TextField mailField;

    @FXML
    PasswordField firstPasswordField;
    @FXML
    PasswordField secondPasswordField;

    @FXML
    private void onRegistrationClick() throws IOException, InterruptedException {

        String name = nameField.getText();
        String mail = mailField.getText();

        String firstPassword = firstPasswordField.getText();
        String secondPassword = secondPasswordField.getText();

        if (name.isEmpty()) {

            infoLabel.setText("Введите логин!");
            return;

        }

        if (mail.isEmpty()) {

            infoLabel.setText("Введите электронную почту!");
            return;

        }

        if (firstPassword.isEmpty() || secondPassword.isEmpty()) {

            infoLabel.setText("Введите пароли!");
            return;

        }

        if (!firstPassword.equals(secondPassword)) {

            infoLabel.setText("Пароли не совпадают!");
            return;

        }

        alkitMessengerClient.getSettings().setRememberMe(true);

        AtomicBoolean register = new AtomicBoolean(false);

        PacketFeedback packetFeedback = new PacketFeedbackBuilder()
                .setWaitThread(Thread.currentThread())
                .addConsumer(PacketFeedback.Reason.EXCEPTION, feedback -> {

                    register.set(false);
                    infoLabel.setText(((ExceptionPacket) feedback.getReceivedPacket()).getMessage());

                })
                .addConsumer(PacketFeedback.Reason.PACKET, feedback -> {

                    if (feedback.getReceivedPacket() instanceof UserDataPacket)
                        register.set(true);

                })
                .build();

        serverConnection.addPacketFeedBack(packetFeedback);
        serverConnection.addPacket(new UserRegistrationPacket(name, mail, firstPassword));

        synchronized (packetFeedback) {

            packetFeedback.wait();

        }

        if (register.get())
            Windows.MAIN.open();

    }

    @FXML
    private void onLoginClick() throws IOException {

        Windows.LOGIN.open();

    }
}