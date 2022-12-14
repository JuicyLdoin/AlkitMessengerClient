package net.alkitmessenger.packet.packets.output;

import lombok.NonNull;
import net.alkitmessenger.client.AlkitMessengerClient;
import net.alkitmessenger.packet.Packet;

import java.io.PrintWriter;

public class UserDisconnectPacket extends Packet {

    public void serialize(@NonNull PrintWriter printWriter) {

        printWriter.println(getID(getClass()));

        writeObject(printWriter, AlkitMessengerClient.getAlkitMessengerClient().getUser());

        printWriter.flush();

    }
}