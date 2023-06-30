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

package de.florianmichael.betapackets.packet.play.s2c.v1_9;

import de.florianmichael.betapackets.base.ModelMapper;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.model.base.ProtocolCollection;
import de.florianmichael.betapackets.model.sound.SoundCategory1_9;
import de.florianmichael.betapackets.model.sound.SoundEvents;

import java.util.Objects;

public class CustomSoundS2CPacket extends Packet {

    public ModelMapper<Integer, SoundEvents> sound = new ModelMapper<>(FunctionalByteBuf::readVarInt, FunctionalByteBuf::writeVarInt, SoundEvents::getById);
    public ModelMapper<Integer, SoundCategory1_9> category = new ModelMapper<>(FunctionalByteBuf::readVarInt, FunctionalByteBuf::writeVarInt, SoundCategory1_9::getById);

    public int x;
    public int y;
    public int z;
    public float soundVolume;
    public int soundPitch;

    public CustomSoundS2CPacket(final FunctionalByteBuf buf) {
        this.sound.read(buf);
        this.category.read(buf);
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.soundVolume = buf.readFloat();
        this.soundPitch = buf.readUnsignedByte();
    }

    public CustomSoundS2CPacket(SoundEvents sound, SoundCategory1_9 category, int x, int y, int z, float soundVolume, int soundPitch) {
        this.sound = new ModelMapper<>(FunctionalByteBuf::writeVarInt, sound);
        this.category = new ModelMapper<>(FunctionalByteBuf::writeVarInt, category);
        this.x = x;
        this.y = y;
        this.z = z;
        this.soundVolume = soundVolume;
        this.soundPitch = soundPitch;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        this.sound.write(buf);
        this.category.write(buf);
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
        return (float) this.soundPitch / 63.5F;
    }

    @Override
    public String toString() {
        return "CustomSoundS2CPacket{" +
                "sound=" + sound +
                ", category=" + category +
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

        CustomSoundS2CPacket that = (CustomSoundS2CPacket) o;

        if (x != that.x) return false;
        if (y != that.y) return false;
        if (z != that.z) return false;
        if (Float.compare(that.soundVolume, soundVolume) != 0) return false;
        if (soundPitch != that.soundPitch) return false;
        if (!Objects.equals(sound, that.sound)) return false;
        return Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        int result = sound != null ? sound.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + x;
        result = 31 * result + y;
        result = 31 * result + z;
        result = 31 * result + (soundVolume != +0.0f ? Float.floatToIntBits(soundVolume) : 0);
        result = 31 * result + soundPitch;
        return result;
    }
}
