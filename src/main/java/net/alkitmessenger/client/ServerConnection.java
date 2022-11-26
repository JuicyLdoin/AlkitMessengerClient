package net.alkitmessenger.client;

import lombok.NonNull;
import lombok.Value;
import net.alkitmessenger.packet.Packet;
import net.alkitmessenger.packet.PacketFeedback;
import net.alkitmessenger.packet.PacketSerialize;
import net.alkitmessenger.packet.packets.ExceptionPacket;
import net.alkitmessenger.packet.packets.output.AuthorizePacket;
import net.alkitmessenger.packet.packets.output.UserConnectPacket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Value
public class ServerConnection extends Thread {

    BufferedReader in;

    Queue<Packet> outPackets;
    PrintWriter out;

    List<PacketFeedback> packetFeedback;

    public ServerConnection(@NonNull String host, short port) {

        try(Socket socket = new Socket(host, port)){

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            outPackets = new LinkedList<>();
            out = new PrintWriter(socket.getOutputStream());

            packetFeedback = new ArrayList<>();

            new AuthorizePacket().serialize(out);
            new UserConnectPacket().serialize(out);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        start();

    }

    public void addPacket(Packet packet) {

        outPackets.add(packet);

    }

    public void addPacketFeedBack(PacketFeedback feedback) {

        packetFeedback.add(feedback);

        new Thread(() -> {

            try {

                sleep(1000 * 10);
                packetFeedback.remove(feedback);

            } catch (InterruptedException e) {

                throw new RuntimeException(e);

            }
        }).start();
    }

    public void run() {

        while (true)
            try {

                // получение пакетов от сервера

                while (in.ready()) {

                    Packet inputPacket = PacketSerialize.serialize(in);

                    List<PacketFeedback> toRemove = new ArrayList<>();

                    packetFeedback.forEach(feedback -> {

                        feedback.setReceivedPacket(inputPacket);
                        feedback.resume(inputPacket instanceof ExceptionPacket ? PacketFeedback.Reason.EXCEPTION : PacketFeedback.Reason.PACKET);

                        if (feedback.isRead())
                            toRemove.add(feedback);

                    });

                    toRemove.forEach(packetFeedback::remove);

                    inputPacket.work();

                    try {

                        outPackets.addAll(inputPacket.feedback());

                    } catch (Exception ignored) {
                    }
                }

                // отправка пакетов серверу из очереди

                while (!outPackets.isEmpty()) {

                    Packet packet = outPackets.poll();

                    if (packet == null)
                        continue;

                    packet.serialize(out);

                }
            } catch (Exception exception) {

                exception.printStackTrace();

            }
    }
}