package net.alkitmessenger.packet;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import net.alkitmessenger.packet.packets.ExceptionPacket;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Consumer;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PacketFeedback {

    Thread waitThread;

    Map<Reason, Consumer<PacketFeedback>> consumerMap;

    Packet receivedPacket;
    Reason reason;

    boolean read = false;

    public PacketFeedback() {}

    public PacketFeedback(@NotNull Map<Reason, Consumer<PacketFeedback>> consumerMap) {

        this.consumerMap = consumerMap;

    }

    public PacketFeedback(@NotNull Thread waitThread, @NotNull Map<Reason, Consumer<PacketFeedback>> consumerMap) {

        this.waitThread = waitThread;
        this.consumerMap = consumerMap;

    }

    public void resume(Reason reason) {

        this.reason = reason;

        consumerMap.keySet()
                .stream()
                .filter(key -> key.equals(reason))
                .forEach(key -> consumerMap.get(key).accept(this));

        synchronized (this) {

            notifyAll();

        }
    }

    public String getException() {

        if (receivedPacket == null)
            throw new NullPointerException();

        if (!(receivedPacket instanceof ExceptionPacket))
            throw new UnsupportedOperationException();

        return ((ExceptionPacket) receivedPacket).getMessage();

    }

    public enum Reason {

        PACKET,
        EXCEPTION

    }
}