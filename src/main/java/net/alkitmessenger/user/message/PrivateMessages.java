package net.alkitmessenger.user.message;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import net.alkitmessenger.user.User;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrivateMessages {

    long id;

    long user1;
    long user2;

    ObservableList<Message> messages;

    public PrivateMessages(@NonNull long user1, @NonNull long user2) {

        id = ThreadLocalRandom.current().nextLong();

        this.user1 = user1;
        this.user2 = user2;

        messages = FXCollections.observableArrayList();

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

    public long getSecondUserID(@NonNull User user) {

        return getSecondUserID(user.getId());

    }

    public long getSecondUserID(@NonNull long user) {

        return user1 == user ? user2 : user1;

    }
}