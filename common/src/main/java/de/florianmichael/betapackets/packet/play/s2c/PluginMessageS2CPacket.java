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
import io.netty.buffer.Unpooled;

import java.util.Objects;

public class PluginMessageS2CPacket extends Packet {

    public String channel;
    public FunctionalByteBuf data;

    public PluginMessageS2CPacket(final FunctionalByteBuf buf) {
        this.channel = buf.readString(20);

        final byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        this.data = new FunctionalByteBuf(Unpooled.buffer().writeBytes(bytes), buf.getUserConnection());
    }

    public PluginMessageS2CPacket(String channel, FunctionalByteBuf data) {
        this.channel = channel;
        this.data = data;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeString(this.channel);
        buf.writeBytes(this.data.getBuffer().array());
    }

    @Override
    public String toString() {
        return "PluginMessageS2CPacket{" +
                "channel='" + channel + '\'' +
                ", data=" + data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PluginMessageS2CPacket that = (PluginMessageS2CPacket) o;

        if (!Objects.equals(channel, that.channel)) return false;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        int result = channel != null ? channel.hashCode() : 0;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }
}
