package net.alkitmessenger.packet.packets.input;

import lombok.Value;
import net.alkitmessenger.client.AlkitMessengerClient;
import net.alkitmessenger.packet.Packet;
import net.alkitmessenger.packet.PacketWorkException;
import net.alkitmessenger.user.User;

@Value
public class UserDataPacket extends Packet {

    User user;

    public void work() throws PacketWorkException {

        if (user == null)
            throw new PacketWorkException();

        AlkitMessengerClient.getAlkitMessengerClient().getUserManager().getUsers().put(user.getId(), user);

    }
}