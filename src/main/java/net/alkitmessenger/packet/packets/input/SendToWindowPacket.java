package net.alkitmessenger.packet.packets.input;

import javafx.fxml.FXMLLoader;
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

        MAIN("/scenes/mainScene.fxml", 0, 0, true, true, false);

        String path;

        int width;
        int height;

        boolean resize;
        boolean maximized;

        boolean fullScreen;

        public void open() throws IOException {

            FXMLLoader fxmlLoader = FXMLLoader.load(getClass().getResource(path));
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