package net.alkitmessenger.packet.packets.output;

import lombok.NonNull;
import lombok.Value;
import net.alkitmessenger.client.AlkitMessengerClient;
import net.alkitmessenger.packet.Packet;

import java.io.PrintWriter;

@Value
public class UserLoginPacket extends Packet {

    String password;

    public void serialize(@NonNull PrintWriter printWriter) {

        writeObject(printWriter, AlkitMessengerClient.getAlkitMessengerClient().getUser());
        writeObject(printWriter, password);

    }
}