package net.alkitmessenger.packet;

import com.google.gson.Gson;
import lombok.NonNull;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Queue;

public abstract class Packet {

    public void work() throws PacketWorkException {
    }

    public Queue<Packet> feedback() {
        throw new UnsupportedOperationException();
    }

    public void serialize(@NonNull PrintWriter printWriter) {
    }
    protected <T> void writeObject(@NonNull PrintWriter printWriter, @NonNull T parent) {

        new Gson().toJson(new PacketData<>(parent), printWriter);
        printWriter.println();

    }

    protected int getID(@NonNull Class<? extends Packet> clazz) {

        for (Packets packets : Packets.values())
            if (packets.getClazz().equals(clazz))
                return packets.getId();

        return -1;

    }
}