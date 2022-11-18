package net.alkitmessenger.packet;

import com.google.gson.Gson;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class PacketSerialize {

    public static Packet serialize(@NonNull BufferedReader bufferedReader) throws IOException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException {

        if (!bufferedReader.ready())
            throw new IllegalAccessException();

        byte packetID = (byte) bufferedReader.read();
        Packets packets = Packets.getByID(packetID);

        if (packets == null)
            throw new NullPointerException();

        List<PacketData<?>> args = new ArrayList<>();

        while (bufferedReader.ready())
            args.add(new Gson().fromJson(bufferedReader.readLine(), PacketData.class));

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