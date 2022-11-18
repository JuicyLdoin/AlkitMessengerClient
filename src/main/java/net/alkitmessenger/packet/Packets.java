package net.alkitmessenger.packet;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import net.alkitmessenger.packet.packets.input.*;
import net.alkitmessenger.packet.packets.output.*;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum Packets {

    USER_DATA_RECEIVE_PACKET((byte) -3, UserDataReceivePacket.class),
    USER_DISCONNECT_PACKET((byte) -2, UserDisconnectPacket.class),
    USER_LOGIN_PACKET((byte) -1, UserLoginPacket.class),

    AUTHORIZE_PACKET((byte) 0, AuthorizePacket.class),

    USER_DATA_PACKET((byte) 1, UserDataPacket.class),
    SEND_TO_WINDOW_PACKET((byte) 2, SendToWindowPacket.class),
    EXCEPTION_PACKET((byte) 3, ExceptionPacket.class);

    byte id;
    Class<? extends Packet> clazz;

    Packets(@NonNull byte id, @NonNull Class<? extends Packet> clazz) {

        this.id = id;
        this.clazz = clazz;

    }

    public static Packets getByID(@NonNull byte id) {

        for (Packets packets : values())
            if (packets.getId() == id)
                return packets;

        return null;

    }
}