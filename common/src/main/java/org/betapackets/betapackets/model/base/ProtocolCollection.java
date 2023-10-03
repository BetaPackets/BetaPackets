/*
 * This file is part of BetaPackets - https://github.com/BetaPackets/BetaPackets
 * Copyright (C) 2023 FlorianMichael/EnZaXD
 * Copyright (C) 2023 DietrichPaul and contributors
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

package org.betapackets.betapackets.model.base;

import org.betapackets.betapackets.mapping.*;
import org.betapackets.betapackets.model.world.chunk.ChunkFormat;
import org.betapackets.betapackets.model.world.chunk.v1_20_1.ChunkFormat1_20_1;
import org.betapackets.betapackets.packet.ids.PacketId1_20_1;
import org.betapackets.betapackets.packet.ids.PacketIdList;
import org.betapackets.betapackets.mapping.*;

// Thanks to my 12-year-old self for this enum, I guess?
public enum ProtocolCollection {

    R1_20_1(763, "1.20/1.20.1", ChunkFormat1_20_1.INSTANCE, new PacketId1_20_1()),

    R1_19_4(762, "1.19.4"),
    R1_19_3(761, "1.19.3"),
    R1_19_2(760, "1.19.2"),
    R1_19(759, "1.19"),

    R1_18_2(758, "1.18.2"),
    R1_18_1(757, "1.18-1.18.1"),

    R1_17_1(756, "1.17.1"),
    R1_17(755, "1.17"),

    R1_16_5(754, "1.16.4-1.16.5"),
    R1_16_3(753, "1.16.3"),
    R1_16_2(751, "1.16.2"),
    R1_16_1(736, "1.16.1"),
    R1_16(735, "1.16"),

    R1_15_2(578, "1.15.2"),
    R1_15_1(575, "1.15.1"),
    R1_15(573, "1.15"),

    R1_14_4(498, "1.14.4"),
    R1_14_3(490, "1.14.3"),
    R1_14_2(485, "1.14.2"),
    R1_14_1(480, "1.14.1"),
    R1_14(477, "1.14"),

    R1_13_2(404, "1.13.2"),
    R1_13_1(401, "1.13.1"),
    R1_13(393, "1.13"),

    R1_12_2(340, "1.12.2"),
    R1_12_1(338, "1.12.1"),
    R1_12(335, "1.12"),

    R1_11_1(316, "1.11.1-1.11.2"),
    R1_11(315, "1.11"),

    R1_10(210, "1.10.x"),

    R1_9_4(110, "1.9.3-1.9.4"),
    R1_9_2(109, "1.9.2"),
    R1_9_1(108, "1.9.1"),
    R1_9(107, "1.9"),

    R1_8(47, "1.8.x"),

    R1_7_10(5, "1.7.6/10"),
    R1_7_5(4, "1.7.2/5");

    private final int protocolId;
    private final String protocolName;

    private PacketIdList packetIdList;

    private ChunkFormat chunkFormat;

    private BlockMapping blockMapping;
    private BlockStateMapping blockStateMapping;
    private MetadataCodecMapping metadataCodecMapping;
    private EntityMapping entityMapping;
    private ItemMapping itemMapping;
    private ParticleMapping particleMapping;
    private PotionEffectMapping potionEffectMapping;

    ProtocolCollection(int protocolId, String protocolName, ChunkFormat chunkFormat, PacketIdList packetIdList) {
        this.protocolId = protocolId;
        this.protocolName = protocolName;
        this.chunkFormat = chunkFormat;
        this.packetIdList = packetIdList;
    }

    ProtocolCollection(int protocolId, String protocolName) {
        this.protocolId = protocolId;
        this.protocolName = protocolName;
    }

    public PacketIdList getPacketIdList() {
        return packetIdList;
    }

    public void setBlockMapping(BlockMapping blockMapping) {
        this.blockMapping = blockMapping;
    }

    public BlockStateMapping getBlockStateMapping() {
        return blockStateMapping;
    }

    public void setBlockStateMapping(BlockStateMapping blockStateMapping) {
        this.blockStateMapping = blockStateMapping;
    }

    public void setMetadataCodecMapping(MetadataCodecMapping metadataCodecMapping) {
        this.metadataCodecMapping = metadataCodecMapping;
    }

    public void setEntityMapping(EntityMapping entityMapping) {
        this.entityMapping = entityMapping;
    }

    public void setItemMapping(ItemMapping itemMapping) {
        this.itemMapping = itemMapping;
    }

    public void setParticleMapping(ParticleMapping particleMapping) {
        this.particleMapping = particleMapping;
    }

    public void setPotionEffectMapping(PotionEffectMapping potionEffectMapping) {
        this.potionEffectMapping = potionEffectMapping;
    }

    public BlockMapping getBlockMapping() {
        return blockMapping;
    }

    public MetadataCodecMapping getMetadataCodecMapping() {
        return metadataCodecMapping;
    }

    public EntityMapping getEntityMapping() {
        return entityMapping;
    }

    public ItemMapping getItemMapping() {
        return itemMapping;
    }

    public ParticleMapping getParticleMapping() {
        return particleMapping;
    }

    public PotionEffectMapping getPotionEffectMapping() {
        return potionEffectMapping;
    }

    public int getProtocolId() {
        return protocolId;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public static ProtocolCollection fromProtocolId(final int protocolId) {
        for (ProtocolCollection protocol : ProtocolCollection.values()) {
            if (protocol.getProtocolId() == protocolId) {
                return protocol;
            }
        }
        return null;
    }

    public boolean isOlderThan(final ProtocolCollection other) {
        return this.ordinal() > other.ordinal();
    }

    public boolean isOlderThanOrEqualTo(final ProtocolCollection other) {
        return this.ordinal() >= other.ordinal();
    }

    public boolean isNewerThan(final ProtocolCollection other) {
        return this.ordinal() < other.ordinal();
    }

    public boolean isNewerThanOrEqualTo(final ProtocolCollection other) {
        return this.ordinal() <= other.ordinal();
    }
}
