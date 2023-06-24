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
import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.hud.bossbar.BossInfoColor1_9;
import de.florianmichael.betapackets.model.hud.bossbar.BossInfoOverlay1_9;
import de.florianmichael.betapackets.model.hud.bossbar.BossbarOperation1_9;
import net.lenni0451.mcstructs.text.ATextComponent;

import java.util.Objects;
import java.util.UUID;

// MCP calls this packet SPacketUpdateEntityNBT???
public class BossBarS2CPacket extends Packet {

    public UUID uuid;
    public ModelMapper<Integer, BossbarOperation1_9> operation = new ModelMapper<>(FunctionalByteBuf::readVarInt, FunctionalByteBuf::writeVarInt, BossbarOperation1_9::getById);
    public ATextComponent name;
    public float percent;
    public ModelMapper<Integer, BossInfoColor1_9> color = new ModelMapper<>(FunctionalByteBuf::readVarInt, FunctionalByteBuf::writeVarInt, BossInfoColor1_9::getById);
    public ModelMapper<Integer, BossInfoOverlay1_9> overlay = new ModelMapper<>(FunctionalByteBuf::readVarInt, FunctionalByteBuf::writeVarInt, BossInfoOverlay1_9::getById);
    public boolean darkenSky;
    public boolean playEndBossMusic;
    public boolean createFog;

    public BossBarS2CPacket(final FunctionalByteBuf buf) {
        this.uuid = buf.readUUID();
        this.operation.read(buf);

        switch (this.operation.getValue()) {
            case ADD:
                this.name = buf.readComponent();
                this.percent = buf.readFloat();
                this.color.read(buf);
                this.overlay.read(buf);
                this.setFlags(buf.readUnsignedByte());
                break;
            case UPDATE_PERCENT:
                this.percent = buf.readFloat();
                break;
            case UPDATE_NAME:
                this.name = buf.readComponent();
                break;
            case UPDATE_STYLE:
                this.color.read(buf);
                this.overlay.read(buf);
                break;
            case UPDATE_PROPERTIES:
                this.setFlags(buf.readUnsignedByte());
                break;
        }
    }

    public BossBarS2CPacket(UUID uuid, BossbarOperation1_9 operation, ATextComponent name, float percent, BossInfoColor1_9 color, BossInfoOverlay1_9 overlay, boolean darkenSky, boolean playEndBossMusic, boolean createFog) {
        this.uuid = uuid;
        this.operation = new ModelMapper<>(FunctionalByteBuf::writeVarInt, operation);
        this.name = name;
        this.percent = percent;
        this.color = new ModelMapper<>(FunctionalByteBuf::writeVarInt, color);
        this.overlay = new ModelMapper<>(FunctionalByteBuf::writeVarInt, overlay);
        this.darkenSky = darkenSky;
        this.playEndBossMusic = playEndBossMusic;
        this.createFog = createFog;
    }

    public int getFlags() {
        int i = 0;

        if (this.darkenSky) i |= 1;
        if (this.playEndBossMusic) i |= 2;
        if (this.createFog) i |= 2;

        return i;
    }

    public void setFlags(int flags) {
        this.darkenSky = (flags & 1) > 0;
        this.playEndBossMusic = (flags & 2) > 0;
        this.createFog = (flags & 2) > 0;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeUUID(this.uuid);
        this.operation.write(buf);

        switch (this.operation.getValue()) {
            case ADD:
                buf.writeComponent(this.name);
                buf.writeFloat(this.percent);
                this.color.write(buf);
                this.overlay.write(buf);
                buf.writeByte(this.getFlags());
                break;
            case UPDATE_PERCENT:
                buf.writeFloat(this.percent);
                break;
            case UPDATE_NAME:
                buf.writeComponent(this.name);
                break;
            case UPDATE_STYLE:
                this.color.write(buf);
                this.overlay.write(buf);
                break;
            case UPDATE_PROPERTIES:
                buf.writeByte(this.getFlags());
                break;
        }
    }

    @Override
    public String toString() {
        return "BossBarS2CPacket{" +
                "uuid=" + uuid +
                ", operation=" + operation +
                ", name=" + name +
                ", percent=" + percent +
                ", color=" + color +
                ", overlay=" + overlay +
                ", darkenSky=" + darkenSky +
                ", playEndBossMusic=" + playEndBossMusic +
                ", createFog=" + createFog +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BossBarS2CPacket that = (BossBarS2CPacket) o;

        if (Float.compare(that.percent, percent) != 0) return false;
        if (darkenSky != that.darkenSky) return false;
        if (playEndBossMusic != that.playEndBossMusic) return false;
        if (createFog != that.createFog) return false;
        if (!Objects.equals(uuid, that.uuid)) return false;
        if (!Objects.equals(operation, that.operation)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(color, that.color)) return false;
        return Objects.equals(overlay, that.overlay);
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (percent != +0.0f ? Float.floatToIntBits(percent) : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (overlay != null ? overlay.hashCode() : 0);
        result = 31 * result + (darkenSky ? 1 : 0);
        result = 31 * result + (playEndBossMusic ? 1 : 0);
        result = 31 * result + (createFog ? 1 : 0);
        return result;
    }
}
