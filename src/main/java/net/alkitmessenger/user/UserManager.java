package net.alkitmessenger.user;

import com.google.gson.GsonBuilder;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import net.alkitmessenger.client.AlkitMessengerClient;
import net.alkitmessenger.client.ServerConnection;
import net.alkitmessenger.packet.PacketFeedback;
import net.alkitmessenger.packet.Packets;
import net.alkitmessenger.packet.packets.input.UserDataPacket;
import net.alkitmessenger.packet.packets.output.UserDataReceivePacket;
import net.alkitmessenger.util.FileUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserManager {

    final Map<Long, User> users = new HashMap<>();
    User currentUser;

    ServerConnection serverConnection = AlkitMessengerClient.getAlkitMessengerClient().getServerConnection();

    public void loadLocalUser() {

        try {

            long id = AlkitMessengerClient.getAlkitMessengerClient().getUser();

            currentUser = loadUser(id);
            users.put(id, currentUser);

            saveUser();

        } catch (Exception e) {

            throw new RuntimeException(e);

        }
    }

    public User loadUser(long id) throws InterruptedException {

        AtomicReference<User> user = new AtomicReference<>(users.get(id));

        if (user.get() == null) {

            PacketFeedback packetFeedback = new PacketFeedback(Thread.currentThread(), Packets.USER_DATA_PACKET, null,
                    packetFeedBack -> user.set(((UserDataPacket) packetFeedBack.getReceivedPacket()).getUser()));

            serverConnection.addPacketFeedBack(packetFeedback);
            serverConnection.addPacket(new UserDataReceivePacket(id));

            synchronized (packetFeedback) {

                packetFeedback.wait();

            }
        }

        return user.get();

    }

    public User getUserByID(long id) throws InterruptedException {

        return users.getOrDefault(id, loadUser(id));

    }

    public User getUserByID(@NonNull String id) throws InterruptedException {

        return getUserByID(Long.parseLong(id));

    }

    public User getUserByNameAndDisplayID(@NonNull String name, @NonNull String displayID) {

        return users.values()
                .stream()
                .filter(user -> user.getDisplayID().equals(displayID))
                .filter(user -> user.getName().equals(name))
                .toList()
                .get(0);

    }

    public void saveUser() throws IOException {

        FileWriter fileWriter = new FileWriter(FileUtil.USER_DATA);

        new GsonBuilder().setPrettyPrinting().create().toJson(currentUser, fileWriter);

        fileWriter.flush();
        fileWriter.close();

    }
}