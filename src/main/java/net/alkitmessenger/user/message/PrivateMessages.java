package net.alkitmessenger.user.message;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import net.alkitmessenger.AlkitMessenger;
import net.alkitmessenger.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@Table(name = "private_messages")
public class PrivateMessages {

    @Id
    long id;

    long user1;
    long user2;

    @ManyToOne(targetEntity = Message.class)
    List<Message> messages;

    public PrivateMessages(@NonNull long user1, @NonNull long user2) {

        id = ThreadLocalRandom.current().nextLong();

        this.user1 = user1;
        this.user2 = user2;

        messages = new ArrayList<>();

    }

    public PrivateMessages(@NonNull User user1, @NonNull User user2) {

        this(user1.getId(), user2.getId());

    }

    public Stream<Message> filter(@NonNull Predicate<Message> predicate) {

        return messages.stream().filter(predicate);

    }

    public boolean hasUser(@NonNull User user) {

        return hasUser(user.getId());

    }

    public boolean hasUser(@NonNull long user) {

        return user1 == user || user2 == user;

    }

    public User getSecondUser(@NonNull User user) {

        return getSecondUser(user.getId());

    }

    public User getSecondUser(@NonNull long user) {

        return AlkitMessenger.getAlkitMessenger().getUserManager().getUserByID(getSecondUserID(user));

    }

    public long getSecondUserID(@NonNull User user) {

        return getSecondUserID(user.getId());

    }

    public long getSecondUserID(@NonNull long user) {

        return user1 == user ? user2 : user1;

    }
}