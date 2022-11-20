package net.alkitmessenger.packet.packets.output;

import net.alkitmessenger.packet.Packet;
import net.alkitmessenger.user.User;
import net.alkitmessenger.user.message.Message;
import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;

public class UserSendMsgPacket extends Packet {

    Message message;

    public UserSendMsgPacket(String text, User author, User recipient) {
        message = new Message(text, author, recipient);
    }

    @Override
    public void serialize(@NotNull PrintWriter printWriter) {

        printWriter.println(message.toString());

        writeObject(printWriter, message);

        printWriter.flush();
    }
}
