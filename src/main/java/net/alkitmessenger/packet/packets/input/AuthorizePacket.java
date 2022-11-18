package net.alkitmessenger.packet.packets.input;

import lombok.Value;
import net.alkitmessenger.server.packet.Packet;
import net.alkitmessenger.server.packet.PacketWorkException;
import net.alkitmessenger.util.AuthorizeCodeUtil;

@Value
public class AuthorizePacket extends Packet {

    String code;

    public void work() throws PacketWorkException {

        if (!code.equals(AuthorizeCodeUtil.CODE))
            throw new PacketWorkException();

    }
}