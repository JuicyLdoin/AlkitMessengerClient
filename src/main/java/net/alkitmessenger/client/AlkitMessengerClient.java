package net.alkitmessenger.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.alkitmessenger.user.UserManager;
import net.alkitmessenger.util.CryptorUtil;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AlkitMessengerClient extends Application {

    private static AlkitMessengerClient alkitMessengerClient;

    public static AlkitMessengerClient getAlkitMessengerClient() {

        return alkitMessengerClient;

    }

    Parent main;
    Scene scene;

    Stage stage;

    final Long user;
    final UserManager userManager;

    final ServerConnection serverConnection;

    public AlkitMessengerClient() {

        alkitMessengerClient = this;

        user = ThreadLocalRandom.current().nextLong();
        userManager = new UserManager();

        serverConnection = new ServerConnection("localhost", (short) 9090);

    }

    public void start(Stage stage) throws IOException {

        this.stage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/mainScene.fxml"));
        main = fxmlLoader.load();

        scene = new Scene(main, 1080, 720);

        stage.setResizable(false);

        stage.setTitle("AlkitMessenger");
        stage.setScene(scene);

        stage.show();



        byte[] test = new byte[]{0, 5, -4, 2, 127, -85, 25};
        StringBuffer[] test1 = CryptorUtil.byteCryptor(test);
        byte[] test2 = CryptorUtil.byteDecryptor(test1);
        for (int i = 0; i < test2.length; i++) {
            System.out.println(test2[i]);
        }
    }

    public void stop() {
        System.exit(-1);
    }
}