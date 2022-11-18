package net.alkitmessenger.user.message;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import net.alkitmessenger.user.User;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Message {

    long id;

    String text;
    long author;

    Date date;

    public Message(@NonNull String text, @NonNull long author) {

        id = ThreadLocalRandom.current().nextLong();

        this.text = text;
        this.author = author;

        date = new Date();

    }

    public Message(@NonNull String text, @NonNull User author) {

        this(text, author.getId());

    }
}