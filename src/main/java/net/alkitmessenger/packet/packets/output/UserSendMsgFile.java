package net.alkitmessenger.packet.packets.output;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.alkitmessenger.packet.Packet;
import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSendMsgFile extends Packet {

    StringBuffer[] cryptBytes;

    long id;

    long author;
    long recipient;

    Date date;

    public UserSendMsgFile(StringBuffer[] cryptBytes, long author, long recipient) {

        id = ThreadLocalRandom.current().nextLong();

        this.cryptBytes = cryptBytes;
        this.author = author;
        this.recipient = recipient;

        date = new Date();
    }

    @Override
    public void serialize(@NotNull PrintWriter printWriter) {

        printWriter.println(recipient);
        printWriter.println(author);
        printWriter.println(date);
        printWriter.println(Arrays.toString(cryptBytes));

        writeObject(printWriter, id);

        printWriter.flush();
    }
}
