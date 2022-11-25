package net.alkitmessenger.packet.packets.output;

import lombok.NonNull;
import lombok.Value;
import net.alkitmessenger.packet.Packet;

import java.io.PrintWriter;

@Value
public class UserRegistrationPacket extends Packet {

    String name;
    String mail;
    String password;

    public UserRegistrationPacket(String name, String mail, String password) {

        this.name = name;
        this.mail = mail;
        this.password = password;

    }

    public void serialize(@NonNull PrintWriter printWriter) {

        printWriter.println(getID(getClass()));

        writeObject(printWriter, name);
        writeObject(printWriter, mail);
        writeObject(printWriter, password);

        printWriter.flush();

    }
}