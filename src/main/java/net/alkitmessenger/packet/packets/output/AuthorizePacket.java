package net.alkitmessenger.packet.packets.output;

import lombok.NonNull;
import lombok.Value;
import net.alkitmessenger.packet.Packet;
import net.alkitmessenger.util.AuthorizeCodeUtil;

import java.io.PrintWriter;

@Value
public class AuthorizePacket extends Packet {

    public void serialize(@NonNull PrintWriter printWriter) {

        printWriter.print(getID(getClass()));
        printWriter.println();

        writeObject(printWriter, AuthorizeCodeUtil.CODE);

        printWriter.flush();

    }
}