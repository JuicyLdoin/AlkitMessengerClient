package net.alkitmessenger.packet.packets.output;

import lombok.NonNull;
import lombok.Value;
import net.alkitmessenger.server.packet.Packet;
import net.alkitmessenger.user.User;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@Value
public class UserDataPacket extends Packet {

    User user;

    @Override
    public void serialize(@NonNull PrintWriter printWriter) {

        printWriter.println(getID(getClass()));

        for (Field field : user.getClass().getFields())
            try {

                if (!Modifier.isTransient(field.getModifiers()))
                   writeField(printWriter, field, user);

            } catch (Exception exception) {

                exception.printStackTrace();

            }
    }
}