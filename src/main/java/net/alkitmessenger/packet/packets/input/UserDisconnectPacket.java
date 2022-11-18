package net.alkitmessenger.packet.packets.input;

import lombok.Value;
import net.alkitmessenger.AlkitMessenger;
import net.alkitmessenger.server.packet.Packet;
import net.alkitmessenger.server.packet.PacketWorkException;
import net.alkitmessenger.user.User;

@Value
public class UserDisconnectPacket extends Packet {

    long uid;

    public void work() throws PacketWorkException {

        User user = AlkitMessenger.getAlkitMessenger().getUserManager().getUserByID(uid);

        if (user == null)
            throw new PacketWorkException();

        user.setLogined(true);

    }
}