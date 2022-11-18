package net.alkitmessenger.user.message;

import lombok.NonNull;
import lombok.Value;
import net.alkitmessenger.user.User;
import net.alkitmessenger.util.HibernateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Value
public class PrivateMessagesManager {

    Map<Long, PrivateMessages> privateMessagesMap = new HashMap<>();

    public PrivateMessagesManager() {

        new Thread(() -> HibernateUtil.createQueryAndCallActionForEach("From PrivateMessages", PrivateMessages.class,
                privateMessage -> privateMessagesMap.put(privateMessage.getId(), privateMessage))).start();

    }

    public PrivateMessages getByID(@NonNull long id) {

        return privateMessagesMap.get(id);

    }

    public List<PrivateMessages> getFromUser(@NonNull User user) {

        return privateMessagesMap.values()
                .stream()
                .filter(privateMessages -> privateMessages.hasUser(user))
                .toList();

    }
}