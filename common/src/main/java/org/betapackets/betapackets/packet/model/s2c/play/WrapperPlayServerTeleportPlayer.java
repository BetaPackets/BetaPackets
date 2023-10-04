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

package org.betapackets.betapackets.packet.model.s2c.play;

import org.betapackets.betapackets.event.PacketEvent;
import org.betapackets.betapackets.netty.base.FunctionalByteBuf;
import org.betapackets.betapackets.packet.model.PacketWrapper;
import org.betapackets.betapackets.packet.type.Packet;
import org.betapackets.betapackets.packet.type.PacketType;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class WrapperPlayServerTeleportPlayer extends PacketWrapper<WrapperPlayServerTeleportPlayer> {

    private double x, y, z;
    private float yaw, pitch;
    private Set<Flag> flags = new HashSet<>();
    private int sequence;

    public WrapperPlayServerTeleportPlayer(PacketEvent event) throws IOException {
        super(event);
    }

    public WrapperPlayServerTeleportPlayer(double x, double y, double z, float yaw, float pitch, int sequence, Flag... flags) {
        super(PacketType.Play.Server.TELEPORT_PLAYER);
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.flags = new HashSet<>(Arrays.asList(flags));
        this.sequence = sequence;
    }

    @Override
    public void read(Packet type, FunctionalByteBuf buf) throws IOException {
        x = buf.readDouble();
        y = buf.readDouble();
        z = buf.readDouble();
        yaw = buf.readFloat();
        pitch = buf.readFloat();

        flags = new HashSet<>();
        byte flagBitfield = buf.readByte();
        for (Flag value : Flag.values()) {
            if ((flagBitfield & value.id) == value.id)
                flags.add(value);
        }

        sequence = buf.readVarInt();
    }

    @Override
    public void copyFrom(WrapperPlayServerTeleportPlayer base) {
        x = base.x;
        y = base.y;
        z = base.z;
        flags = base.flags;
        sequence = base.sequence;
    }

    @Override
    public void write(Packet type, FunctionalByteBuf buf) throws IOException {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeFloat(yaw);
        buf.writeFloat(pitch);

        byte flagBitfield = 0;
        for (Flag value : Flag.values()) {
            if (flags.contains(value))
                flagBitfield |= value.id;
        }
        buf.writeByte(flagBitfield);

        buf.writeVarInt(sequence);
    }

    enum Flag {

        X, Y, Z, YAW, PITCH;

        private final int id;

        Flag() {
            id = 1 << ordinal();
        }

        public int getId() {
            return id;
        }
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public Set<Flag> getFlags() {
        return flags;
    }

    public void setFlags(Set<Flag> flags) {
        this.flags = flags;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return "WrapperPlayServerTeleportPlayer{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", yaw=" + yaw +
                ", pitch=" + pitch +
                ", flags=" + flags +
                ", sequence=" + sequence +
                '}';
    }
}
