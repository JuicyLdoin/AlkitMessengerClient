package net.alkitmessenger.packet.packets.input;

import lombok.Value;
import net.alkitmessenger.client.Windows;
import net.alkitmessenger.packet.Packet;
import net.alkitmessenger.packet.PacketWorkException;

import java.io.IOException;

@Value
public class SendToWindowPacket extends Packet {

    String path;

    public void work() throws PacketWorkException {

        Windows windows = Windows.getWindowByPath(path);

        if (windows == null)
            throw new PacketWorkException();

        try {

            windows.open();

        } catch (IOException e) {

            throw new RuntimeException(e);

        }
    }
}