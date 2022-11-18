package net.alkitmessenger.packet.packets.output;

import lombok.NonNull;
import lombok.Value;
import net.alkitmessenger.server.packet.Packet;

import java.io.PrintWriter;

@Value
public class ExceptionPacket extends Packet {

    String exception;

    public void serialize(@NonNull PrintWriter printWriter) {

        writeObject(printWriter, exception);

    }
}