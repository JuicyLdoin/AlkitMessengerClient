package net.alkitmessenger.packet.packets.output;

import lombok.NonNull;
import lombok.Value;
import net.alkitmessenger.packet.Packet;
import net.alkitmessenger.user.User;

import java.io.PrintWriter;

@Value
public class UserUpdatePacket extends Packet {

    User user;

    public void serialize(@NonNull PrintWriter printWriter) {

        writeObject(printWriter, getID(getClass()));

        writeObject(printWriter, user);

        printWriter.flush();

    }
}