package net.alkitmessenger.packet.packets.output;

import lombok.NonNull;
import lombok.Value;
import net.alkitmessenger.packet.Packet;

import java.io.PrintWriter;

@Value
public class UserDataReceivePacket extends Packet {

    Long uid;

    public void serialize(@NonNull PrintWriter printWriter) {

        printWriter.println(getID(getClass()));

        writeObject(printWriter, uid);

        printWriter.flush();

    }
}