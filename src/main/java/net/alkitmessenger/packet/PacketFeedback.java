package net.alkitmessenger.packet;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Consumer;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PacketFeedback {

    final Thread waitThread;

    final Packets packet;
    final String exception;

    final Map<Reason, Consumer<PacketFeedback>> consumerMap;

    Packet receivedPacket;
    Reason reason;

    public PacketFeedback(@NotNull Thread waitThread, @Nullable Packets packet, @Nullable String exception, @NotNull Map<Reason, Consumer<PacketFeedback>> consumerMap) {

        this.waitThread = waitThread;

        this.packet = packet;
        this.exception = exception;

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

    public enum Reason {

        PACKET,
        EXCEPTION

    }
}