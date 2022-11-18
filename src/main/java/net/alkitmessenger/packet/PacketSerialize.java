package net.alkitmessenger.packet;

import com.google.gson.Gson;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@UtilityClass
public class PacketSerialize {

    public static Packet serialize(@NonNull Scanner scanner) throws InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException {

        if (!scanner.hasNext())
            throw new IllegalAccessException();

        byte packetID = scanner.nextByte();
        Packets packets = Packets.getByID(packetID);

        if (packets == null)
            throw new NullPointerException();

        List<PacketData<?>> args = new ArrayList<>();

        while (scanner.hasNext())
            args.add(new Gson().fromJson(scanner.nextLine(), PacketData.class));

        Class<?>[] classes = new Class<?>[args.size()];
        Object[] objects = new Object[args.size()];

        for (int i = 0; i < args.size(); i++) {

            PacketData<?> packetData = args.get(i);

            classes[i] = Class.forName(packetData.getClassPath());
            objects[i] = packetData.deserialize();

        }

        return packets.getClazz().getConstructor(classes).newInstance(objects);

    }
}