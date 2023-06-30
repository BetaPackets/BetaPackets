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

package de.florianmichael.betapackets.packet.play.s2c;

import de.florianmichael.betapackets.base.ModelMapper;
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.base.ProtocolCollection;
import de.florianmichael.betapackets.model.game.CombatEvent;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.components.StringComponent;

import java.util.Objects;

public class CombatEventS2CPacket extends Packet {

    public ModelMapper<Integer, CombatEvent> event = new ModelMapper<>(FunctionalByteBuf::readVarInt, FunctionalByteBuf::writeVarInt, CombatEvent::getById);
    public int duration;
    public int playerId;
    public int entityId;
    public ATextComponent deathMessage;

    public CombatEventS2CPacket(final FunctionalByteBuf buf) {
        this.event.read(buf);

        if (this.event.value == 1 /* End Combat */) {
            this.duration = buf.readVarInt();
            this.entityId = buf.readInt();
        }

        if (this.event.value == 2 /* Entity Died */) {
            this.playerId = buf.readVarInt();
            this.entityId = buf.readInt();
            if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
                this.deathMessage = buf.readComponent();
            } else {
                this.deathMessage = new StringComponent(buf.readString());
            }
        }
    }

    public CombatEventS2CPacket() {
        this.event = new ModelMapper<>(FunctionalByteBuf::writeVarInt, CombatEvent.ENTER_COMBAT);
    }

    public CombatEventS2CPacket(int duration, int entityId) {
        this.event = new ModelMapper<>(FunctionalByteBuf::writeVarInt, CombatEvent.END_COMBAT);
        this.duration = duration;
        this.entityId = entityId;
    }

    public CombatEventS2CPacket(int playerId, int entityId, String deathMessage) {
        this(playerId, entityId, new StringComponent(deathMessage));
    }

    public CombatEventS2CPacket(CombatEvent event, int duration, int playerId, int entityId, String deathMessage) {
        this(event, duration, playerId, entityId, new StringComponent(deathMessage));
    }

    public CombatEventS2CPacket(int playerId, int entityId, ATextComponent deathMessage) {
        this.event = new ModelMapper<>(FunctionalByteBuf::writeVarInt, CombatEvent.ENTITY_DIED);
        this.playerId = playerId;
        this.entityId = entityId;
        this.deathMessage = deathMessage;
    }

    public CombatEventS2CPacket(CombatEvent event, int duration, int playerId, int entityId, ATextComponent deathMessage) {
        this.event = new ModelMapper<>(FunctionalByteBuf::writeVarInt, event);
        this.duration = duration;
        this.playerId = playerId;
        this.entityId = entityId;
        this.deathMessage = deathMessage;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        this.event.write(buf);
        if (this.event.value == 1 /* End Combat */) {
            buf.writeVarInt(this.duration);
            buf.writeInt(this.entityId);
        }

        if (this.event.value == 2 /* Entity Died */) {
            buf.writeVarInt(this.playerId);
            buf.writeInt(this.entityId);
            if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
                buf.writeComponent(this.deathMessage);
            } else {
                buf.writeString(this.deathMessage.asSingleString());
            }
        }
    }

    @Override
    public String toString() {
        return "CombatEventS2CPacket{" +
                "event=" + event +
                ", duration=" + duration +
                ", playerId=" + playerId +
                ", entityId=" + entityId +
                ", deathMessage='" + deathMessage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CombatEventS2CPacket that = (CombatEventS2CPacket) o;

        if (duration != that.duration) return false;
        if (playerId != that.playerId) return false;
        if (entityId != that.entityId) return false;
        if (!Objects.equals(event, that.event)) return false;
        return Objects.equals(deathMessage, that.deathMessage);
    }

    @Override
    public int hashCode() {
        int result = event != null ? event.hashCode() : 0;
        result = 31 * result + duration;
        result = 31 * result + playerId;
        result = 31 * result + entityId;
        result = 31 * result + (deathMessage != null ? deathMessage.hashCode() : 0);
        return result;
    }
}
