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

import de.florianmichael.betapackets.base.Packet;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.game.GameStateTypes;

public class ChangeGameStateS2CPacket extends Packet {

    public GameStateTypes reason;
    public float value;

    public ChangeGameStateS2CPacket(final FunctionalByteBuf buf) {
        this(
                GameStateTypes.getById(buf.getProtocolVersion(), buf.readUnsignedByte()),
                buf.readFloat()
        );
    }

    public ChangeGameStateS2CPacket(GameStateTypes reason, float value) {
        this.reason = reason;
        this.value = value;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeByte(reason.getId(buf.getProtocolVersion()));
        buf.writeFloat(value);
    }

    @Override
    public String toString() {
        return "ChangeGameStateS2CPacket{" +
                "reason=" + reason +
                ", value=" + value +
                '}';
    }
}
