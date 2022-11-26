package net.alkitmessenger.packet.packets.input;

import lombok.Value;
import net.alkitmessenger.client.AlkitMessengerClient;
import net.alkitmessenger.packet.Packet;
import net.alkitmessenger.packet.PacketWorkException;
import net.alkitmessenger.user.User;
import net.alkitmessenger.user.UserManager;

@Value
public class UserDataPacket extends Packet {

    User user;

    public void work() throws PacketWorkException {

        if (user == null)
            throw new PacketWorkException();

        UserManager userManager = AlkitMessengerClient.getAlkitMessengerClient().getUserManager();

        if (userManager.getCurrentUser().getId() == user.getId())
            userManager.setCurrentUser(user);

        userManager.getUsers().put(user.getId(), user);

    }
}