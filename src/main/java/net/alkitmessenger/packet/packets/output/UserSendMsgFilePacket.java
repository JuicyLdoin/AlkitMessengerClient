package net.alkitmessenger.packet.packets.output;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.alkitmessenger.packet.Packet;
import net.alkitmessenger.user.User;
import net.alkitmessenger.user.message.Message;
import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSendMsgFilePacket extends Packet {

    Message message;
    public UserSendMsgFilePacket(StringBuffer[] cryptBytes, User author, User recipient) {
        message = new Message(cryptBytes, author, recipient);
    }

    @Override
    public void serialize(@NotNull PrintWriter printWriter) {

        printWriter.println(message);

        writeObject(printWriter, message);

        printWriter.flush();
    }
}
