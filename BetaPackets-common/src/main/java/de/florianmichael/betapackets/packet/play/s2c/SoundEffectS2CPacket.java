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
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.model.base.ProtocolCollection;
import de.florianmichael.betapackets.model.sound.SoundCategory1_9;
import de.florianmichael.betapackets.model.sound.SoundEvents;

import java.util.Objects;

public class SoundEffectS2CPacket extends Packet {

    // 1.8
    public String soundName;

    // 1.9
    public ModelMapper<Integer, SoundEvents> sound1_9 = new ModelMapper<>(FunctionalByteBuf::readVarInt, FunctionalByteBuf::writeVarInt, SoundEvents::getById);
    public ModelMapper<Integer, SoundCategory1_9> category1_9 = new ModelMapper<>(FunctionalByteBuf::readVarInt, FunctionalByteBuf::writeVarInt, SoundCategory1_9::getById);

    public int x;
    public int y;
    public int z;
    public float soundVolume;
    public int soundPitch;

    public SoundEffectS2CPacket(final FunctionalByteBuf buf) {
        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            this.sound1_9.read(buf);
            this.category1_9.read(buf);
        } else {
            this.soundName = buf.readString(256);
        }

        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.soundVolume = buf.readFloat();
        this.soundPitch = buf.readUnsignedByte();
    }

    public SoundEffectS2CPacket(String soundName, int x, int y, int z, float soundVolume, int soundPitch) {
        this.soundName = soundName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.soundVolume = soundVolume;
        this.soundPitch = soundPitch;
    }

    public SoundEffectS2CPacket(SoundEvents sound, SoundCategory1_9 category, int x, int y, int z, float soundVolume, int soundPitch) {
        this.sound1_9 = new ModelMapper<>(FunctionalByteBuf::writeVarInt, sound);
        this.category1_9 = new ModelMapper<>(FunctionalByteBuf::writeVarInt, category);
        this.x = x;
        this.y = y;
        this.z = z;
        this.soundVolume = soundVolume;
        this.soundPitch = soundPitch;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            this.sound1_9.write(buf);
            this.category1_9.write(buf);
        } else {
            buf.writeString(soundName);
        }
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeFloat(soundVolume);
        buf.writeByte(soundPitch);
    }

    public double getX() {
        return (float) this.x / 8.0F;
    }

    public double getY() {
        return (float) this.y / 8.0F;
    }

    public double getZ() {
        return (float) this.z / 8.0F;
    }

    public float getPitch(final ProtocolCollection version) {
        if (version.isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            return (float) this.soundPitch / 63.5F;
        } else {
            return (float) this.soundPitch / 63.0F;
        }
    }

    @Override
    public String toString() {
        return "SoundEffectS2CPacket{" +
                "soundName='" + soundName + '\'' +
                ", sound1_9=" + sound1_9 +
                ", category1_9=" + category1_9 +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", soundVolume=" + soundVolume +
                ", soundPitch=" + soundPitch +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SoundEffectS2CPacket that = (SoundEffectS2CPacket) o;

        if (x != that.x) return false;
        if (y != that.y) return false;
        if (z != that.z) return false;
        if (Float.compare(that.soundVolume, soundVolume) != 0) return false;
        if (soundPitch != that.soundPitch) return false;
        if (!Objects.equals(soundName, that.soundName)) return false;
        if (!Objects.equals(sound1_9, that.sound1_9)) return false;
        return Objects.equals(category1_9, that.category1_9);
    }

    @Override
    public int hashCode() {
        int result = soundName != null ? soundName.hashCode() : 0;
        result = 31 * result + (sound1_9 != null ? sound1_9.hashCode() : 0);
        result = 31 * result + (category1_9 != null ? category1_9.hashCode() : 0);
        result = 31 * result + x;
        result = 31 * result + y;
        result = 31 * result + z;
        result = 31 * result + (soundVolume != +0.0f ? Float.floatToIntBits(soundVolume) : 0);
        result = 31 * result + soundPitch;
        return result;
    }
}
