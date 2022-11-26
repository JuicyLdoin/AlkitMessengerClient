package net.alkitmessenger.user.message;

import lombok.NonNull;
import lombok.Value;
import net.alkitmessenger.user.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Value
public class PrivateMessagesManager {

    Map<Long, PrivateMessages> privateMessagesMap = new HashMap<>();

    public PrivateMessages getByID(long id) {

        return privateMessagesMap.get(id);

    }

    public List<PrivateMessages> getFromUser(@NonNull User user) {

        return privateMessagesMap.values()
                .stream()
                .filter(privateMessages -> privateMessages.hasUser(user))
                .toList();

    }
}