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

import de.florianmichael.betapackets.base.packet.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.base.ProtocolCollection;
import de.florianmichael.betapackets.model.position.MapData;

import java.util.Arrays;

public class MapS2CPacket extends Packet {

    public int mapId;
    public byte mapScale;
    public boolean trackingPosition_1_9;
    public MapData[] visiblePlayers;
    public int minX;
    public int minY;
    public int maxX;
    public int maxY;
    public byte[] mapData;

    public MapS2CPacket(final FunctionalByteBuf buf) {
        this.mapId = buf.readVarInt();
        this.mapScale = buf.readByte();
        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            this.trackingPosition_1_9 = buf.readBoolean();
        }
        this.visiblePlayers = new MapData[buf.readVarInt()];
        for (int i = 0; i < this.visiblePlayers.length; i++) {
            final short indices = buf.readByte();

            this.visiblePlayers[i] = new MapData((byte) (indices >> 4 & 15), buf.readByte(), buf.readByte(), (byte) (indices & 15));
        }
        this.maxX = buf.readUnsignedByte();
        if (this.maxX > 0) {
            this.maxY = buf.readUnsignedByte();
            this.minX = buf.readUnsignedByte();
            this.minY = buf.readUnsignedByte();
            this.mapData = buf.readByteArray();
        }
    }

    public MapS2CPacket(int mapId, byte mapScale, MapData[] visiblePlayers, int minX, int minY, int maxX, int maxY, byte[] mapData) {
        this.mapId = mapId;
        this.mapScale = mapScale;
        this.visiblePlayers = visiblePlayers;
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        this.mapData = mapData;
    }

    public MapS2CPacket(int mapId, byte mapScale, boolean trackingPosition, MapData[] visiblePlayers, int minX, int minY, int maxX, int maxY, byte[] mapData) {
        this.mapId = mapId;
        this.mapScale = mapScale;
        this.trackingPosition_1_9 = trackingPosition;
        this.visiblePlayers = visiblePlayers;
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        this.mapData = mapData;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeVarInt(this.mapId);
        buf.writeByte(this.mapScale);
        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            buf.writeBoolean(this.trackingPosition_1_9);
        }
        buf.writeVarInt(this.visiblePlayers.length);
        for (final MapData visiblePlayer : this.visiblePlayers) {
            buf.writeByte((visiblePlayer.x & 15) << 4 | visiblePlayer.z & 15);
            buf.writeByte(visiblePlayer.y);
            buf.writeByte(visiblePlayer.w); // direction
        }
        buf.writeByte(this.maxX);
        if (this.maxX > 0) {
            buf.writeByte(this.maxY);
            buf.writeByte(this.minX);
            buf.writeByte(this.minY);
            buf.writeByteArray(this.mapData);
        }
    }

    @Override
    public String toString() {
        return "MapS2CPacket{" +
                "mapId=" + mapId +
                ", mapScale=" + mapScale +
                ", trackingPosition_1_9=" + trackingPosition_1_9 +
                ", visiblePlayers=" + Arrays.toString(visiblePlayers) +
                ", minX=" + minX +
                ", minY=" + minY +
                ", maxX=" + maxX +
                ", maxY=" + maxY +
                ", mapData=" + Arrays.toString(mapData) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MapS2CPacket that = (MapS2CPacket) o;

        if (mapId != that.mapId) return false;
        if (mapScale != that.mapScale) return false;
        if (trackingPosition_1_9 != that.trackingPosition_1_9) return false;
        if (minX != that.minX) return false;
        if (minY != that.minY) return false;
        if (maxX != that.maxX) return false;
        if (maxY != that.maxY) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(visiblePlayers, that.visiblePlayers)) return false;
        return Arrays.equals(mapData, that.mapData);
    }

    @Override
    public int hashCode() {
        int result = mapId;
        result = 31 * result + (int) mapScale;
        result = 31 * result + (trackingPosition_1_9 ? 1 : 0);
        result = 31 * result + Arrays.hashCode(visiblePlayers);
        result = 31 * result + minX;
        result = 31 * result + minY;
        result = 31 * result + maxX;
        result = 31 * result + maxY;
        result = 31 * result + Arrays.hashCode(mapData);
        return result;
    }
}