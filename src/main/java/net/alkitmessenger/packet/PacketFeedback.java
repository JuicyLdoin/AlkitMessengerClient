package net.alkitmessenger.packet;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public record PacketFeedback(@NotNull Thread waitThread, @Nullable Packets packet, @Nullable String exception, @NotNull Consumer<Reason> consumer) {

    public void resume(Reason reason) {

        synchronized (waitThread) {

            waitThread.notify();

        }

        consumer.accept(reason);

    }

    public enum Reason {

        PACKET,
        EXCEPTION

    }
}