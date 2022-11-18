package net.alkitmessenger.user;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserManager {

    final Map<Long, User> users = new HashMap<>();
    User currentUser;

    public UserManager() {

        currentUser = null;

    }

    public void loadUser(long id) {

        

    }

    public User getUserByID(@NonNull long id) {

        return users.get(id);

    }

    public User getUserByID(@NonNull String id) {

        return users.get(Long.parseLong(id));

    }

    public User getUserByNameAndDisplayID(@NonNull String name, @NonNull String displayID) {

        return users.values()
                .stream()
                .filter(user -> user.getDisplayID().equals(displayID))
                .filter(user -> user.getName().equals(name))
                .toList()
                .get(0);

    }

    public void saveUser() {

    }
}