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

package de.florianmichael.betapackets.packet.play.c2s;

import de.florianmichael.betapackets.base.ModelMapper;
import de.florianmichael.betapackets.base.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.base.ProtocolCollection;
import de.florianmichael.betapackets.model.position.Vec3;

import java.util.Objects;

public class UseEntityC2SPacket extends Packet {

    public int entityId;
    public ModelMapper<Integer, TAction> action = new ModelMapper<>(FunctionalByteBuf::readVarInt, FunctionalByteBuf::writeVarInt, TAction::getById);
    public Vec3 hitVec;

    public UseEntityC2SPacket(final FunctionalByteBuf buf) {
        this.entityId = buf.readVarInt();
        this.action.read(buf);

        if (this.action.getValue() == TAction.INTERACT_AT) {
            this.hitVec = new Vec3((double)buf.readFloat(), (double)buf.readFloat(), (double)buf.readFloat());
        }
    }

    public UseEntityC2SPacket(int entityId, TAction action, Vec3 hitVec) {
        this.entityId = entityId;
        this.action = new ModelMapper<>(FunctionalByteBuf::writeVarInt, action);
        this.hitVec = hitVec;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(this.entityId);

        this.action.write(buf);
        if (this.action.getValue() == TAction.INTERACT_AT) {
            buf.writeFloat((float)this.hitVec.x);
            buf.writeFloat((float)this.hitVec.y);
            buf.writeFloat((float)this.hitVec.z);
        }
    }

    @Override
    public String toString() {
        return "UseEntityC2SPacket{" +
                "entityId=" + entityId +
                ", action=" + action +
                ", hitVec=" + hitVec +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UseEntityC2SPacket that = (UseEntityC2SPacket) o;

        if (entityId != that.entityId) return false;
        if (!Objects.equals(action, that.action)) return false;
        return Objects.equals(hitVec, that.hitVec);
    }

    @Override
    public int hashCode() {
        int result = entityId;
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (hitVec != null ? hitVec.hashCode() : 0);
        return result;
    }

    public enum TAction {
        INTERACT,
        ATTACK,
        INTERACT_AT;

        public static TAction getById(final ProtocolCollection version, final int id) {
            for (TAction value : values()) {
                if (value.ordinal() == id) return value;
            }
            return null;
        }
    }
}
