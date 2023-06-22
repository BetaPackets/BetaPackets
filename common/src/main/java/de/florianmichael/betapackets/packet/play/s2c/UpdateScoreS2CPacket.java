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
import de.florianmichael.betapackets.base.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.base.ProtocolCollection;

import java.util.Objects;

public class UpdateScoreS2CPacket extends Packet {

    public String name;
    public ModelMapper<Integer, TAction> action = new ModelMapper<>(FunctionalByteBuf::readVarInt, FunctionalByteBuf::writeVarInt, TAction::getById);
    public String objective;
    public int value;

    public UpdateScoreS2CPacket(final FunctionalByteBuf buf) {
        this.name = buf.readString(40);
        this.action.read(buf);
        this.objective = buf.readString(16);

        if (this.action.value != 1) {
            this.value = buf.readVarInt();
        }
    }

    public UpdateScoreS2CPacket(String name, String objective, int value, TAction action) {
        this.name = name;
        this.objective = objective;
        this.value = value;
        this.action = new ModelMapper<>(FunctionalByteBuf::writeVarInt, action);
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeString(this.name);
        this.action.write(buf);
        buf.writeString(this.objective);

        if (this.action.value != 1) {
            this.action.write(buf);
        }
    }

    @Override
    public String toString() {
        return "UpdateScoreS2CPacket{" +
                "name='" + name + '\'' +
                ", action=" + action +
                ", objective='" + objective + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateScoreS2CPacket that = (UpdateScoreS2CPacket) o;

        if (value != that.value) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(action, that.action)) return false;
        return Objects.equals(objective, that.objective);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (objective != null ? objective.hashCode() : 0);
        result = 31 * result + value;
        return result;
    }

    public enum TAction {

        CHANGE,
        REMOVE;

        public static TAction getById(final ProtocolCollection version, final int id) {
            for (TAction value : values()) {
                if (value.ordinal() == id) return value;
            }
            return null;
        }
    }
}
