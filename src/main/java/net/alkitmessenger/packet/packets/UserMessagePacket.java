package net.alkitmessenger.packet.packets;

import lombok.NonNull;
import lombok.Value;
import net.alkitmessenger.client.AlkitMessengerClient;
import net.alkitmessenger.packet.Packet;
import net.alkitmessenger.packet.PacketWorkException;
import net.alkitmessenger.user.message.Message;
import net.alkitmessenger.user.message.PrivateMessages;

import java.io.PrintWriter;

@Value
public class UserMessagePacket extends Packet {

    Long id;
    Message message;

    PrivateMessages privateMessages;

    public UserMessagePacket(Long id, Message message) {

        this.id = id;
        this.message = message;

        privateMessages = AlkitMessengerClient.getAlkitMessengerClient().getPrivateMessagesManager().getByID(id);

    }

    public void work() throws PacketWorkException {

        if (privateMessages == null)
            throw new NullPointerException("PrivateMessages not found");
        else {


        }
    }

    public void serialize(@NonNull PrintWriter printWriter) {

        printWriter.println(getID(getClass()));

        writeObject(printWriter, id);
        writeObject(printWriter, message);

        printWriter.flush();

    }
}