package net.alkitmessenger.client;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

@Getter

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum Windows {

    LOGIN("/scenes/authorize/loginScene.fxml", 320, 400, false, false, false),
    REGISTER("/scenes/authorize/registrationScene.fxml", 400, 500, false, false, false),

    MAIN("/scenes/mainScene.fxml", 1080, 720, false, false, false);

    String path;

    int width;
    int height;

    boolean resize;
    boolean maximized;

    boolean fullScreen;

    public static @Nullable Windows getWindowByPath(String path) {

        for (Windows window : values())
            if (window.getPath().equals(path))
                return window;

        return null;

    }

    public void open() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        AlkitMessengerClient.getAlkitMessengerClient().getScene().setRoot(fxmlLoader.load());

        Stage stage = AlkitMessengerClient.getAlkitMessengerClient().getStage();

        stage.setWidth(width);
        stage.setHeight(height);

        stage.setResizable(resize);
        stage.setMaximized(maximized);

        stage.setFullScreen(fullScreen);

    }
}