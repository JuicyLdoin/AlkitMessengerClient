package net.alkitmessenger.user.message;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import net.alkitmessenger.user.User;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message {

    Long id;

    StringBuffer[] cryptBytes;
    String text = "Not text";

    Long author;

    Date date;

    public Message(@NonNull String text, User author) {

        id = ThreadLocalRandom.current().nextLong();

        this.text = text;
        this.author = author.getId();

        date = new Date();

    }

    public Message(@NonNull StringBuffer[] cryptBytes, User author) {

        id = ThreadLocalRandom.current().nextLong();

        this.cryptBytes = cryptBytes;
        this.author = author.getId();

        date = new Date();

    }

    @Override
    public String toString() {

        return "Message{" +
                "id=" + id +
                ", cryptBytes=" + Arrays.toString(cryptBytes) +
                ", text='" + text + '\'' +
                ", author=" + author +
                ", date=" + date +
                '}';

    }
}