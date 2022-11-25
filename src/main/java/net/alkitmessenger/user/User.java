package net.alkitmessenger.user;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class User {

    long id;
    String displayID;

    String name;
    String password;

    List<User> friends;

    Date creationDate;

    boolean logined;

    public boolean equalsPassword(@NonNull String password) {

        return this.password.equals(password);

    }
}