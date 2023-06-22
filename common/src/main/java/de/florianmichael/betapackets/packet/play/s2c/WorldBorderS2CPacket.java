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
import de.florianmichael.betapackets.model.WorldBorderAction;

import java.util.Objects;

public class WorldBorderS2CPacket extends Packet {

    public ModelMapper<Integer, WorldBorderAction> action = new ModelMapper<>(FunctionalByteBuf::readVarInt, FunctionalByteBuf::writeVarInt, WorldBorderAction::getById);
    public int size;
    public double centerX;
    public double centerZ;
    public double targetSize;
    public double diameter;
    public long timeUntilTarget;
    public int warningTime;
    public int warningDistance;

    public WorldBorderS2CPacket(final FunctionalByteBuf buf) {
        this.action.read(buf);
        if (this.action.getValue() == null) return;

        switch (this.action.getValue()) {
            case SET_SIZE:
                this.targetSize = buf.readDouble();
                break;
            case LERP_SIZE:
                this.diameter = buf.readDouble();
                this.targetSize = buf.readDouble();
                this.timeUntilTarget = buf.readVarLong();
                break;
            case SET_CENTER:
                this.centerX = buf.readDouble();
                this.centerZ = buf.readDouble();
                break;
            case SET_WARNING_BLOCKS:
                this.warningDistance = buf.readVarInt();
                break;
            case SET_WARNING_TIME:
                this.warningTime = buf.readVarInt();
                break;
            case INITIALIZE:
                this.centerX = buf.readDouble();
                this.centerZ = buf.readDouble();
                this.diameter = buf.readDouble();
                this.targetSize = buf.readDouble();
                this.timeUntilTarget = buf.readVarLong();
                this.size = buf.readVarInt();
                this.warningDistance = buf.readVarInt();
                this.warningTime = buf.readVarInt();
                break;
        }
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        this.action.write(buf);
        switch (this.action.getValue()) {
            case SET_SIZE:
                buf.writeDouble(this.targetSize);
                break;
            case LERP_SIZE:
                buf.writeDouble(this.diameter);
                buf.writeDouble(this.targetSize);
                buf.writeVarLong(this.timeUntilTarget);
                break;
            case SET_CENTER:
                buf.writeDouble(this.centerX);
                buf.writeDouble(this.centerZ);
                break;
            case SET_WARNING_BLOCKS:
                buf.writeVarInt(this.warningDistance);
                break;
            case SET_WARNING_TIME:
                buf.writeVarInt(this.warningTime);
                break;
            case INITIALIZE:
                buf.writeDouble(this.centerX);
                buf.writeDouble(this.centerZ);
                buf.writeDouble(this.diameter);
                buf.writeDouble(this.targetSize);
                buf.writeVarLong(this.timeUntilTarget);
                buf.writeVarInt(this.size);
                buf.writeVarInt(this.warningDistance);
                buf.writeVarInt(this.warningTime);
                break;
        }
    }

    @Override
    public String toString() {
        return "WorldBorderS2CPacket{" +
                "action=" + action +
                ", size=" + size +
                ", centerX=" + centerX +
                ", centerZ=" + centerZ +
                ", targetSize=" + targetSize +
                ", diameter=" + diameter +
                ", timeUntilTarget=" + timeUntilTarget +
                ", warningTime=" + warningTime +
                ", warningDistance=" + warningDistance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorldBorderS2CPacket that = (WorldBorderS2CPacket) o;

        if (size != that.size) return false;
        if (Double.compare(that.centerX, centerX) != 0) return false;
        if (Double.compare(that.centerZ, centerZ) != 0) return false;
        if (Double.compare(that.targetSize, targetSize) != 0) return false;
        if (Double.compare(that.diameter, diameter) != 0) return false;
        if (timeUntilTarget != that.timeUntilTarget) return false;
        if (warningTime != that.warningTime) return false;
        if (warningDistance != that.warningDistance) return false;
        return Objects.equals(action, that.action);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = action != null ? action.hashCode() : 0;
        result = 31 * result + size;
        temp = Double.doubleToLongBits(centerX);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(centerZ);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(targetSize);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(diameter);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (timeUntilTarget ^ (timeUntilTarget >>> 32));
        result = 31 * result + warningTime;
        result = 31 * result + warningDistance;
        return result;
    }
}
