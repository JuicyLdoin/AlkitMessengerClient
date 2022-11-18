package net.alkitmessenger.packet.packets.input;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import net.alkitmessenger.client.AlkitMessengerClient;
import net.alkitmessenger.packet.Packet;
import net.alkitmessenger.packet.PacketWorkException;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

@Value
public class SendToWindowPacket extends Packet {

    String path;

    public void work() throws PacketWorkException {

        Windows windows = Windows.getWindowByPath(path);

        if (windows == null)
            throw new PacketWorkException();

        try {

            windows.open();

        } catch (IOException e) {

            throw new RuntimeException(e);

        }
    }

    @Getter

    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public enum Windows {

        LOGIN("/scenes/authorize/loginScene.fxml", 320, 400, false, false, false),
        REGISTER("/scenes/authorize/registrationScene.fxml", 400, 500, false, false, false),

        MAIN("/scenes/mainScene.fxml", 0, 0, true, true, false);

        String path;

        int width;
        int height;

        boolean resize;
        boolean maximized;

        boolean fullScreen;

        public void open() throws IOException {

            FXMLLoader fxmlLoader = FXMLLoader.load(getClass().getResource(path));
            Stage stage = AlkitMessengerClient.getAlkitMessengerClient().getStage();

            stage.setWidth(width);
            stage.setHeight(height);

            stage.setResizable(resize);
            stage.setMaximized(maximized);

            stage.setFullScreen(fullScreen);

            AlkitMessengerClient.getAlkitMessengerClient().getScene().setRoot(fxmlLoader.load());

        }

        public static @Nullable Windows getWindowByPath(String path) {

            for (Windows window : values())
                if (window.getPath().equals(path))
                    return window;

            return null;

        }
    }
}