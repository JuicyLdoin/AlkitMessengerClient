package net.alkitmessenger.packet;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PacketFeedback {

    final Thread waitThread;

    final Packets packet;
    final String exception;

    final Consumer<PacketFeedback> consumer;

    Reason reason;

    public PacketFeedback(@NotNull Thread waitThread, @Nullable Packets packet, @Nullable String exception, @NotNull Consumer<PacketFeedback> consumer) {

        this.waitThread = waitThread;

        this.packet = packet;
        this.exception = exception;

        this.consumer = consumer;

    }

    public void resume(Reason reason) {

        this.reason = reason;

        synchronized (waitThread) {

            waitThread.notify();

        }

        consumer.accept(this);

    }

    public enum Reason {

        PACKET,
        EXCEPTION

    }
}