package net.alkitmessenger.packet.packets.input;

import lombok.Value;
import net.alkitmessenger.packet.Packet;
import net.alkitmessenger.packet.PacketWorkException;

@Value
public class ExceptionPacket extends Packet {

    String message;

    public void work() throws PacketWorkException {

        throw new RuntimeException(message);

    }
}