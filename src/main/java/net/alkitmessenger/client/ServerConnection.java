package net.alkitmessenger.client;

import lombok.NonNull;
import lombok.Value;
import net.alkitmessenger.packet.Packet;
import net.alkitmessenger.packet.PacketSerialize;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

@Value
public class ServerConnection extends Thread {

    BufferedReader in;

    Queue<Packet> outPackets;
    PrintWriter out;

    public ServerConnection(@NonNull String host, @NotNull short port) {

        try {

            Socket socket = new Socket(host, port);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            outPackets = new LinkedList<>();
            out = new PrintWriter(socket.getOutputStream());

        } catch (IOException e) {

            throw new RuntimeException(e);

        }

        start();

    }

    public void addPacket(Packet packet) {

        outPackets.add(packet);

    }

    public void run() {

        while(true)
            try {

                // получение пакетов от сервера

                while (in.ready())
                    PacketSerialize.serialize(in).work();

                // отправка пакетов серверу из очереди

                while (!outPackets.isEmpty()) {

                    Packet packet = outPackets.poll();

                    if (packet == null)
                        continue;

                    packet.serialize(out);

                }
            } catch (Exception ignored) {}
    }
}