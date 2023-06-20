package de.florianmichael.betapackets.netty.event;

import de.florianmichael.betapackets.netty.BetaPacketsPipeline;

public class ReorderPipelineEvent {
    public String targetDecoder;
    public String targetEncoder;

    public ReorderPipelineEvent() {
        this(BetaPacketsPipeline.HANDLER_PACKET_DECODER_NAME, BetaPacketsPipeline.HANDLER_PACKET_ENCODER_NAME);
    }

    public ReorderPipelineEvent(String targetDecoder, String targetEncoder) {
        this.targetDecoder = targetDecoder;
        this.targetEncoder = targetEncoder;
    }
}
