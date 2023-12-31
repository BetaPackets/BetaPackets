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
import org.betapackets.betapackets.netty.base.FunctionalByteBuf;
import org.betapackets.betapackets.packet.model.PacketWrapper;
import org.betapackets.betapackets.packet.type.Packet;
import org.betapackets.betapackets.packet.type.PacketType;

import java.io.IOException;

public class WrapperPlayClientFlying extends PacketWrapper<WrapperPlayClientFlying> {

    private double x, y, z;
    private float yaw, pitch;
    private boolean onGround;

    private boolean positionUpdate;
    private boolean lookUpdate;

    public WrapperPlayClientFlying(PacketEvent event) throws IOException {
        super(event);
    }

    public WrapperPlayClientFlying(double x, double y, double z, float yaw, float pitch, boolean onGround, boolean positionUpdate, boolean lookUpdate) {
        super(positionUpdate && lookUpdate ? PacketType.Play.Client.POSITION_LOOK : positionUpdate
                ? PacketType.Play.Client.POSITION : lookUpdate ? PacketType.Play.Client.LOOK : PacketType.Play.Client.FLYING);
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
        this.positionUpdate = positionUpdate;
        this.lookUpdate = lookUpdate;
    }

    public WrapperPlayClientFlying(double x, double y, double z, float yaw, float pitch, boolean onGround) {
        this(x, y, z, yaw, pitch, onGround, true, true);
    }

    public WrapperPlayClientFlying(double x, double y, double z, boolean onGround) {
        this(x, y, z, 0, 0, onGround, true, false);
    }

    public WrapperPlayClientFlying(float yaw, float pitch, boolean onGround) {
        this(0, 0, 0, yaw, pitch, onGround, false, true);
    }

    public WrapperPlayClientFlying(boolean onGround) {
        this(0, 0, 0, 0, 0, onGround, false, false);
    }

    @Override
    public void write(Packet type, FunctionalByteBuf buf) throws IOException {
        type = positionUpdate && lookUpdate ? PacketType.Play.Client.POSITION_LOOK : positionUpdate
                ? PacketType.Play.Client.POSITION : lookUpdate ? PacketType.Play.Client.LOOK : PacketType.Play.Client.FLYING;

        if (type == PacketType.Play.Client.POSITION || type == PacketType.Play.Client.POSITION_LOOK) {
            buf.writeDouble(x);
            buf.writeDouble(y);
            buf.writeDouble(z);
        }
        if (type == PacketType.Play.Client.LOOK || type == PacketType.Play.Client.POSITION_LOOK) {
            buf.writeFloat(yaw);
            buf.writeFloat(pitch);
        }
        buf.writeBoolean(onGround);
    }

    @Override
    public void read(Packet type, FunctionalByteBuf buf) throws IOException {
        lookUpdate = type == PacketType.Play.Client.LOOK || type == PacketType.Play.Client.POSITION_LOOK;
        positionUpdate = type == PacketType.Play.Client.POSITION || type == PacketType.Play.Client.POSITION_LOOK;

        if (positionUpdate) {
            x = buf.readDouble();
            y = buf.readDouble();
            z = buf.readDouble();
        }

        if (lookUpdate) {
            yaw = buf.readFloat();
            pitch = buf.readFloat();
        }

        onGround = buf.readBoolean();
    }

    @Override
    public void copyFrom(WrapperPlayClientFlying base) {
        x = base.x;
        y = base.y;
        z = base.z;
        yaw = base.yaw;
        pitch = base.pitch;
        onGround = base.onGround;
        positionUpdate = base.positionUpdate;
        lookUpdate = base.lookUpdate;
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

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public boolean isPositionUpdate() {
        return positionUpdate;
    }

    public void setPositionUpdate(boolean positionUpdate) {
        this.positionUpdate = positionUpdate;
    }

    public boolean isLookUpdate() {
        return lookUpdate;
    }

    public void setLookUpdate(boolean lookUpdate) {
        this.lookUpdate = lookUpdate;
    }

    public static boolean isFlying(Packet packet) {
        return packet == PacketType.Play.Client.FLYING
                || packet == PacketType.Play.Client.POSITION
                || packet == PacketType.Play.Client.POSITION_LOOK
                || packet == PacketType.Play.Client.LOOK;
    }

    @Override
    public String toString() {
        if (positionUpdate && lookUpdate) {
            return "WrapperPlayClientFlying{" +
                    "type=POSITION_LOOK" +
                    ", x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    ", yaw=" + yaw +
                    ", pitch=" + pitch +
                    ", onGround=" + onGround +
                    '}';
        } else if (positionUpdate) {
            return "WrapperPlayClientFlying{" +
                    "type=POSITION" +
                    ", x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    ", onGround=" + onGround +
                    '}';
        } else if (lookUpdate) {
            return "WrapperPlayClientFlying{" +
                    "type=LOOK" +
                    ", yaw=" + yaw +
                    ", pitch=" + pitch +
                    ", onGround=" + onGround +
                    '}';
        }
        return "WrapperPlayClientFlying{" +
                "type=FLYING" +
                ", onGround=" + onGround +
                '}';
    }
}
