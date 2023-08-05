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

package de.florianmichael.betapackets.packet.model.play.c2s;

import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.event.PacketEvent;
import de.florianmichael.betapackets.model.base.ProtocolCollection;
import de.florianmichael.betapackets.model.entity.v1_9.Hand1_9;
import de.florianmichael.betapackets.packet.model.PacketWrapper;
import de.florianmichael.betapackets.packet.type.Packet;

import java.io.IOException;

public class WrapperPlayClientUseEntity extends PacketWrapper<WrapperPlayClientUseEntity> {

    private int entityId;
    private Action action;
    private float hitVecX;
    private float hitVecY;
    private float hitVecZ;
    private Hand1_9 hand;
    private boolean sneaking;

    public WrapperPlayClientUseEntity(PacketEvent event) throws IOException {
        super(event);
    }

    public WrapperPlayClientUseEntity(int entityId, Action action, float hitVecX, float hitVecY, float hitVecZ, Hand1_9 hand, boolean sneaking) {
        this.entityId = entityId;
        this.action = action;
        this.hitVecX = hitVecX;
        this.hitVecY = hitVecY;
        this.hitVecZ = hitVecZ;
        this.hand = hand;
        this.sneaking = sneaking;
    }

    public WrapperPlayClientUseEntity(int entityId, boolean sneaking) {
        this(entityId, Action.ATTACK, 0, 0, 0, null, sneaking);
    }

    public WrapperPlayClientUseEntity(int entityId, boolean sneaking, Hand1_9 hand) {
        this(entityId, Action.INTERACT, 0, 0, 0, hand, sneaking);
    }

    public WrapperPlayClientUseEntity(int entityId, boolean sneaking, Hand1_9 hand, float hitVecX, float hitVecY, float hitVecZ) {
        this(entityId, Action.INTERACT_AT, hitVecX, hitVecY, hitVecZ, hand, sneaking);
    }

    @Override
    public void write(Packet type, FunctionalByteBuf buf) throws IOException {
        buf.writeVarInt(entityId);
        buf.writeVarInt(action.getId());
        if (action == Action.INTERACT_AT) {
            buf.writeFloat(hitVecX);
            buf.writeFloat(hitVecY);
            buf.writeFloat(hitVecZ);
            buf.writeVarInt(hand.getId(buf.getProtocolVersion()));
        } else if (action == Action.INTERACT) {
            buf.writeVarInt(hand.getId(buf.getProtocolVersion()));
        }
        buf.writeBoolean(sneaking);
    }

    @Override
    public void read(Packet type, FunctionalByteBuf buf) throws IOException {
        entityId = buf.readVarInt();
        action = Action.getById(buf.getProtocolVersion(), buf.readVarInt());
        if (action == Action.INTERACT_AT) {
            hitVecX = buf.readFloat();
            hitVecY = buf.readFloat();
            hitVecZ = buf.readFloat();
            hand = Hand1_9.getById(buf.getProtocolVersion(), buf.readVarInt());
        } else if (action == Action.INTERACT) {
            hand = Hand1_9.getById(buf.getProtocolVersion(), buf.readVarInt());
        }
        sneaking = buf.readBoolean();
    }

    @Override
    public void copyFrom(WrapperPlayClientUseEntity base) {
        entityId = base.entityId;
        action = base.action;
        hitVecX = base.hitVecX;
        hitVecY = base.hitVecY;
        hitVecZ = base.hitVecZ;
        hand = base.hand;
        sneaking = base.sneaking;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public float getHitVecX() {
        return hitVecX;
    }

    public void setHitVecX(float hitVecX) {
        this.hitVecX = hitVecX;
    }

    public float getHitVecY() {
        return hitVecY;
    }

    public void setHitVecY(float hitVecY) {
        this.hitVecY = hitVecY;
    }

    public float getHitVecZ() {
        return hitVecZ;
    }

    public void setHitVecZ(float hitVecZ) {
        this.hitVecZ = hitVecZ;
    }

    public Hand1_9 getHand() {
        return hand;
    }

    public void setHand(Hand1_9 hand) {
        this.hand = hand;
    }

    public boolean isSneaking() {
        return sneaking;
    }

    public void setSneaking(boolean sneaking) {
        this.sneaking = sneaking;
    }

    @Override
    public String toString() {
        if (action == Action.ATTACK) {
            return "WrapperPlayClientUseEntity{" +
                    "entityId=" + entityId +
                    ", action=" + action +
                    ", sneaking=" + sneaking +
                    '}';
        } else if (action == Action.INTERACT) {
            return "WrapperPlayClientUseEntity{" +
                    "entityId=" + entityId +
                    ", action=" + action +
                    ", hand=" + hand +
                    ", sneaking=" + sneaking +
                    '}';
        } else if (action == Action.INTERACT_AT) {
            return "WrapperPlayClientUseEntity{" +
                    "entityId=" + entityId +
                    ", action=" + action +
                    ", hitVecX=" + hitVecX +
                    ", hitVecY=" + hitVecY +
                    ", hitVecZ=" + hitVecZ +
                    ", hand=" + hand +
                    ", sneaking=" + sneaking +
                    '}';
        }
        throw new IllegalArgumentException("Unknown action " + action);
    }

    public enum Action {

        INTERACT(0),
        ATTACK(1),
        INTERACT_AT(2);

        private final int id;

        Action(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static Action getById(ProtocolCollection version, int id) {
            for (Action action : values())
                if (action.id == id)
                    return action;
            return null;
        }
    }
}
