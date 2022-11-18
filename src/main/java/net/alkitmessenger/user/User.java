package net.alkitmessenger.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import net.alkitmessenger.util.IDUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@Table(name = "users")
public class User {

    @Id
    long id;
    String displayID;

    String name;
    String password;

    List<Long> friends;

    Date creationDate;

    boolean logined;

    public User(@NonNull String name) {

        id = ThreadLocalRandom.current().nextLong();
        displayID = IDUtil.generateRandomID(3);

        this.name = name;

        friends = new ArrayList<>();

        creationDate = new Date();

        logined = false;

    }

    public boolean equalsPassword(@NonNull String password) {

        return this.password.equals(password);

    }
}