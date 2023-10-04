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

package org.betapackets.betapackets.packet.model.c2s.play;

import org.betapackets.betapackets.event.PacketEvent;
import org.betapackets.betapackets.model.entity.v1_9.Hand1_9;
import org.betapackets.betapackets.netty.base.FunctionalByteBuf;
import org.betapackets.betapackets.packet.model.PacketWrapper;
import org.betapackets.betapackets.packet.type.Packet;
import org.betapackets.betapackets.packet.type.PacketType;

import java.io.IOException;

public class WrapperPlayClientUseItem extends PacketWrapper<WrapperPlayClientUseItem> {

    private Hand1_9 hand;
    private int sequence;

    public WrapperPlayClientUseItem(PacketEvent event) throws IOException {
        super(event);
    }

    public WrapperPlayClientUseItem(Hand1_9 hand, int sequence) {
        super(PacketType.Play.Client.USE_ITEM);
        this.hand = hand;
        this.sequence = sequence;
    }

    @Override
    public void write(Packet type, FunctionalByteBuf buf) throws IOException {
        buf.writeEnumConstant(hand);
        buf.writeVarInt(sequence);
    }

    @Override
    public void read(Packet type, FunctionalByteBuf buf) throws IOException {
        hand = buf.readEnumConstant(Hand1_9.class);
        sequence = buf.readVarInt();
    }

    @Override
    public void copyFrom(WrapperPlayClientUseItem base) {
        hand = base.hand;
        sequence = base.sequence;
    }

    public Hand1_9 getHand() {
        return hand;
    }

    public void setHand(Hand1_9 hand) {
        this.hand = hand;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return "WrapperPlayClientUseItem{" +
                "hand=" + hand +
                ", sequence=" + sequence +
                '}';
    }
}
