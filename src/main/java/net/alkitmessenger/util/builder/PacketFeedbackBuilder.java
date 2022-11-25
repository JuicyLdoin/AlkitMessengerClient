package net.alkitmessenger.util.builder;

import net.alkitmessenger.packet.PacketFeedback;

import java.util.function.Consumer;

public class PacketFeedbackBuilder implements IBuilder<PacketFeedback> {

    private final PacketFeedback packetFeedback;

    public PacketFeedbackBuilder() {

        packetFeedback = new PacketFeedback();

    }

    public PacketFeedbackBuilder setWaitThread(Thread thread) {

        packetFeedback.setWaitThread(thread);
        return this;

    }

    public PacketFeedbackBuilder addConsumer(PacketFeedback.Reason reason, Consumer<PacketFeedback> packetFeedbackConsumer) {

        packetFeedback.getConsumerMap().put(reason, packetFeedbackConsumer);
        return this;

    }

    public PacketFeedback build() {

        return packetFeedback;

    }
}