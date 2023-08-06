/*
 * This file is part of BetaPackets - https://github.com/FlorianMichael/BetaPackets
 * Copyright (C) 2023 FlorianMichael/EnZaXD and contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.florianmichael.betapackets.api.netty.event;

import de.florianmichael.betapackets.api.netty.BetaPacketsPipeline;

/**
 * This event is fired when the pipeline is reordered by the compression and can be used to reorder the whole BetaPackets pipeline after adding it.
 */
public class ReorderPipelineEvent {

    /**
     * The name of the decoder and encoder where the BetaPackets Pipeline should be reordered too.
     */
    public String targetDecoder;
    public String targetEncoder;

    /**
     * Creates a new ReorderPipelineEvent with the default decoder and encoder names.
     */
    public ReorderPipelineEvent() {
        this(BetaPacketsPipeline.HANDLER_PACKET_DECODER_NAME, BetaPacketsPipeline.HANDLER_PACKET_ENCODER_NAME);
    }

    /**
     * Creates a new ReorderPipelineEvent with the given decoder and encoder names.
     * @param targetDecoder The name of the decoder where the BetaPackets Pipeline should be reordered too.
     * @param targetEncoder The name of the encoder where the BetaPackets Pipeline should be reordered too.
     */
    public ReorderPipelineEvent(String targetDecoder, String targetEncoder) {
        this.targetDecoder = targetDecoder;
        this.targetEncoder = targetEncoder;
    }
}
