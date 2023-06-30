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
import de.florianmichael.betapackets.model.hud.TitleType;
import net.lenni0451.mcstructs.text.ATextComponent;

import java.util.Objects;

public class TitleS2CPacket extends Packet {
    public ModelMapper<Integer, TitleType> type = new ModelMapper<>(FunctionalByteBuf::readVarInt, FunctionalByteBuf::writeVarInt, TitleType::getById);
    public ATextComponent message;
    public int fadeInTime;
    public int displayTime;
    public int fadeOutTime;

    public TitleS2CPacket(final FunctionalByteBuf buf) {
        this.type.read(buf);
        if (this.type.getValue() == null) return;

        if (this.type.getValue() == TitleType.TITLE || this.type.getValue() == TitleType.SUBTITLE) {
            this.message = buf.readComponent();
        }
        if (this.type.getValue() == TitleType.TIMES) {
            this.fadeInTime = buf.readInt();
            this.displayTime = buf.readInt();
            this.fadeOutTime = buf.readInt();
        }
    }

    public TitleS2CPacket(TitleType type, ATextComponent message, int fadeInTime, int displayTime, int fadeOutTime) {
        this.type = new ModelMapper<>(FunctionalByteBuf::writeVarInt, type);
        this.message = message;
        this.fadeInTime = fadeInTime;
        this.displayTime = displayTime;
        this.fadeOutTime = fadeOutTime;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        this.type.write(buf);
        if (this.type.getValue() == TitleType.TITLE || this.type.getValue() == TitleType.SUBTITLE) {
            buf.writeComponent(message);
        }
        if (this.type.getValue() == TitleType.TIMES) {
            buf.writeInt(this.fadeInTime);
            buf.writeInt(this.displayTime);
            buf.writeInt(this.fadeOutTime);
        }
    }

    @Override
    public String toString() {
        return "TitleS2CPacket{" +
                "type=" + type +
                ", message=" + message +
                ", fadeInTime=" + fadeInTime +
                ", displayTime=" + displayTime +
                ", fadeOutTime=" + fadeOutTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TitleS2CPacket that = (TitleS2CPacket) o;

        if (fadeInTime != that.fadeInTime) return false;
        if (displayTime != that.displayTime) return false;
        if (fadeOutTime != that.fadeOutTime) return false;
        if (!Objects.equals(type, that.type)) return false;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + fadeInTime;
        result = 31 * result + displayTime;
        result = 31 * result + fadeOutTime;
        return result;
    }
}
