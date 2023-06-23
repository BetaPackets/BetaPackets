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
import de.florianmichael.betapackets.model.ClassicChatFormattings;
import de.florianmichael.betapackets.model.base.ProtocolCollection;
import de.florianmichael.betapackets.model.game.hud.teams.CollisionRule1_9;
import de.florianmichael.betapackets.model.game.hud.teams.TeamsMode;
import de.florianmichael.betapackets.model.game.hud.teams.TeamsVisible;

import java.util.Arrays;
import java.util.Objects;

public class TeamsS2CPacket extends Packet {

    public String teamName;
    public ModelMapper<Byte, TeamsMode> mode = new ModelMapper<>(FunctionalByteBuf::readByte, FunctionalByteBuf::writeByte, TeamsMode::getById);

    public String teamDisplayName;
    public String teamPrefix;
    public String teamSuffix;
    public int friendlyFire;
    public ModelMapper<String, TeamsVisible> nameTagVisibility = new ModelMapper<>(buf -> buf.readString(32), FunctionalByteBuf::writeString, TeamsVisible::getByName);
    public ModelMapper<String, CollisionRule1_9> collisionRule1_9 = new ModelMapper<>(buf -> buf.readString(32), FunctionalByteBuf::writeString, CollisionRule1_9::getByName);
    public ModelMapper<Byte, ClassicChatFormattings> color = new ModelMapper<>(FunctionalByteBuf::readByte, FunctionalByteBuf::writeByte, ClassicChatFormattings::getById);

    public String[] players;

    public TeamsS2CPacket(final FunctionalByteBuf buf) {
        this.teamName = buf.readString(16);
        this.mode.read(buf);

        if (this.mode.value == 0 /* Create */ || this.mode.value == 2 /* Update Information */) {
            this.teamDisplayName = buf.readString(32);
            this.teamPrefix = buf.readString(16);
            this.teamSuffix = buf.readString(16);
            this.friendlyFire = buf.readByte();
            this.nameTagVisibility.read(buf);
            if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
                this.collisionRule1_9.read(buf);
            }
            this.color.read(buf);
        }

        if (this.mode.value == 0 /* Create */ || this.mode.value == 3 /* Add Player */ || this.mode.value == 4 /* Remove Player */) {
            players = new String[buf.readVarInt()];
            for (int i = 0; i < players.length; i++) {
                players[i] = buf.readString(40);
            }
        }
    }

    public TeamsS2CPacket(String teamName, TeamsMode mode, String teamDisplayName, String teamPrefix, String teamSuffix, int friendlyFire, TeamsVisible nameTagVisibility, ClassicChatFormattings color, String[] players) {
        this.teamName = teamName;
        this.mode = new ModelMapper<>(FunctionalByteBuf::writeByte, mode);
        this.teamDisplayName = teamDisplayName;
        this.teamPrefix = teamPrefix;
        this.teamSuffix = teamSuffix;
        this.friendlyFire = friendlyFire;
        this.nameTagVisibility = new ModelMapper<>(FunctionalByteBuf::writeString, nameTagVisibility);
        this.color = new ModelMapper<>(FunctionalByteBuf::writeByte, color);
        this.players = players;
    }

    public TeamsS2CPacket(String teamName, TeamsMode mode, String teamDisplayName, String teamPrefix, String teamSuffix, int friendlyFire, TeamsVisible nameTagVisibility, CollisionRule1_9 collisionRule, ClassicChatFormattings color, String[] players) {
        this.teamName = teamName;
        this.mode = new ModelMapper<>(FunctionalByteBuf::writeByte, mode);
        this.teamDisplayName = teamDisplayName;
        this.teamPrefix = teamPrefix;
        this.teamSuffix = teamSuffix;
        this.friendlyFire = friendlyFire;
        this.nameTagVisibility = new ModelMapper<>(FunctionalByteBuf::writeString, nameTagVisibility);
        this.collisionRule1_9 = new ModelMapper<>(FunctionalByteBuf::writeString, collisionRule);
        this.color = new ModelMapper<>(FunctionalByteBuf::writeByte, color);
        this.players = players;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        buf.writeString(this.teamName);
        this.mode.write(buf);

        if (this.mode.value == 0 /* Create */ || this.mode.value == 2 /* Update Information */) {
            buf.writeString(this.teamDisplayName);
            buf.writeString(this.teamPrefix);
            buf.writeString(this.teamSuffix);
            buf.writeByte(this.friendlyFire);
            this.nameTagVisibility.write(buf);
            if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
                this.collisionRule1_9.write(buf);
            }
            this.color.write(buf);
        }

        if (this.mode.value == 0 /* Create */ || this.mode.value == 3 /* Add Player */ || this.mode.value == 4 /* Remove Player */) {
            buf.writeVarInt(this.players.length);
            for (String player : this.players) {
                buf.writeString(player);
            }
        }
    }

    @Override
    public String toString() {
        return "TeamsS2CPacket{" +
                "teamName='" + teamName + '\'' +
                ", mode=" + mode +
                ", teamDisplayName='" + teamDisplayName + '\'' +
                ", teamPrefix='" + teamPrefix + '\'' +
                ", teamSuffix='" + teamSuffix + '\'' +
                ", friendlyFire=" + friendlyFire +
                ", nameTagVisibility='" + nameTagVisibility + '\'' +
                ", color=" + color +
                ", players=" + Arrays.toString(players) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeamsS2CPacket that = (TeamsS2CPacket) o;

        if (friendlyFire != that.friendlyFire) return false;
        if (!Objects.equals(teamName, that.teamName)) return false;
        if (!Objects.equals(mode, that.mode)) return false;
        if (!Objects.equals(teamDisplayName, that.teamDisplayName))
            return false;
        if (!Objects.equals(teamPrefix, that.teamPrefix)) return false;
        if (!Objects.equals(teamSuffix, that.teamSuffix)) return false;
        if (!Objects.equals(nameTagVisibility, that.nameTagVisibility))
            return false;
        if (!Objects.equals(color, that.color)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(players, that.players);
    }

    @Override
    public int hashCode() {
        int result = teamName != null ? teamName.hashCode() : 0;
        result = 31 * result + (mode != null ? mode.hashCode() : 0);
        result = 31 * result + (teamDisplayName != null ? teamDisplayName.hashCode() : 0);
        result = 31 * result + (teamPrefix != null ? teamPrefix.hashCode() : 0);
        result = 31 * result + (teamSuffix != null ? teamSuffix.hashCode() : 0);
        result = 31 * result + friendlyFire;
        result = 31 * result + (nameTagVisibility != null ? nameTagVisibility.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(players);
        return result;
    }
}
