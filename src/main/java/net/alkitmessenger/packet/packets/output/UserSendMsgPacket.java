package net.alkitmessenger.packet.packets.output;

import lombok.Value;
import net.alkitmessenger.packet.Packet;
import net.alkitmessenger.user.User;
import net.alkitmessenger.user.message.Message;
import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;

@Value
public class UserSendMsgPacket extends Packet {

    Long id;
    Message message;

    public UserSendMsgPacket(long id, String text, User author, User recipient) {

        this.id = id;
        message = new Message(text, author, recipient);

    }

    @Override
    public void serialize(@NotNull PrintWriter printWriter) {

        printWriter.println(getID(getClass()));

        writeObject(printWriter, message);

        printWriter.flush();

    }
}
