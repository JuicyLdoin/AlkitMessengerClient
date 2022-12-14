package net.alkitmessenger.client;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.alkitmessenger.packet.packets.output.UserDisconnectPacket;
import net.alkitmessenger.user.User;
import net.alkitmessenger.user.UserManager;
import net.alkitmessenger.user.message.PrivateMessagesManager;
import net.alkitmessenger.util.FileUtil;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AlkitMessengerClient extends Application {

    private static AlkitMessengerClient alkitMessengerClient;
    final Settings settings;
    final Long user;
    final UserManager userManager;
    final PrivateMessagesManager privateMessagesManager;
    final ServerConnection serverConnection;
    Parent main;
    Scene scene;
    Stage stage;

    public AlkitMessengerClient() throws IOException, InterruptedException {

        alkitMessengerClient = this;

        File userData = FileUtil.USER_DATA;

        if (userData.exists())
            user = new Gson().fromJson(new FileReader(userData), User.class).getId();
        else {

            userData.createNewFile();
            user = Math.abs(ThreadLocalRandom.current().nextLong());

        }

        File userSettings = FileUtil.USER_SETTINGS;

        if (userSettings.exists())
            settings = new Gson().fromJson(new FileReader(userSettings), Settings.class);
        else {

            userSettings.createNewFile();
            settings = new Settings();

        }

        serverConnection = new ServerConnection("localhost", (short) 9090);

        userManager = new UserManager();
        userManager.loadLocalUser();

        privateMessagesManager = new PrivateMessagesManager();

    }

    public static AlkitMessengerClient getAlkitMessengerClient() {

        return alkitMessengerClient;

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

        User currentUser = userManager.getCurrentUser();

        if (currentUser.equalsPassword(""))
            Windows.REGISTER.open();
        else if (settings.isRememberMe())
            Windows.MAIN.open();
        else
            Windows.LOGIN.open();

    }

    public void stop() throws IOException {

        userManager.saveUser();
        settings.save();

        serverConnection.addPacket(new UserDisconnectPacket());

        System.exit(-1);

    }
}