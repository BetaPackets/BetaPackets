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
import de.florianmichael.betapackets.model.auth.GameProfile;
import de.florianmichael.betapackets.model.auth.ProfileProperty;
import de.florianmichael.betapackets.model.game.hud.tablist.AddPlayerData;
import de.florianmichael.betapackets.model.game.hud.tablist.TabAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerListItemS2CPacket extends Packet {

    public ModelMapper<Integer, TabAction> action = new ModelMapper<>(FunctionalByteBuf::readVarInt, FunctionalByteBuf::writeVarInt, TabAction::getById);
    public List<AddPlayerData> players;

    public PlayerListItemS2CPacket(final FunctionalByteBuf buf) {
        action.read(buf);
        players = new ArrayList<>();
        final int playerSize = buf.readVarInt();
        for (int i = 0; i < playerSize; i++) {
            final AddPlayerData addPlayerData = new AddPlayerData();
            addPlayerData.gameProfile = new GameProfile(buf.readUUID(), null);

            switch (action.mappedValue) {
                case ADD_PLAYER:
                    addPlayerData.gameProfile.name = buf.readString(16);

                    final int properties = buf.readVarInt();
                    for (int i1 = 0; i1 < properties; ++i1) {
                        final String name = buf.readString(32767);
                        final String value = buf.readString(32767);

                        if (buf.readBoolean() /* is signed */) {
                            addPlayerData.gameProfile.profileProperties.add(new ProfileProperty(name, value, buf.readString(32767)));
                        } else {
                            addPlayerData.gameProfile.profileProperties.add(new ProfileProperty(name, value));
                        }
                    }
                    addPlayerData.gameMode.read(buf);
                    addPlayerData.ping = buf.readVarInt();
                    if (buf.readBoolean()) { // has display name
                        addPlayerData.displayName = buf.readComponent();
                    }
                    break;
                case UPDATE_GAME_MODE:
                    addPlayerData.gameMode.read(buf);
                    break;
                case UPDATE_LATENCY:
                    addPlayerData.ping = buf.readVarInt();
                    break;
                case UPDATE_DISPLAY_NAME:
                    if (buf.readBoolean()) { // has display name
                        addPlayerData.displayName = buf.readComponent();
                    }
                    break;
            }
            players.add(addPlayerData);
        }
    }

    public PlayerListItemS2CPacket(TabAction action, List<AddPlayerData> players) {
        this.action = new ModelMapper<>(FunctionalByteBuf::writeVarInt, action);
        this.players = players;
    }

    @Override
    public void write(FunctionalByteBuf buf) throws Exception {
        this.action.write(buf);
        buf.writeVarInt(this.players.size());
        for (AddPlayerData player : players) {
            buf.writeUUID(player.gameProfile.uuid);
            switch (action.value) {
                case 0: // ADD_PLAYER
                    buf.writeString(player.gameProfile.name);

                    buf.writeVarInt(player.gameProfile.profileProperties.size());
                    for (ProfileProperty property : player.gameProfile.profileProperties) {
                        buf.writeString(property.name);
                        buf.writeString(property.value);
                        if (property.signature != null) {
                            buf.writeBoolean(true);
                            buf.writeString(property.signature);
                        } else {
                            buf.writeBoolean(false);
                        }
                    }
                    player.gameMode.write(buf);
                    buf.writeVarInt(player.ping);
                    if (player.displayName != null) {
                        buf.writeBoolean(true);
                        buf.writeComponent(player.displayName);
                    } else {
                        buf.writeBoolean(false);
                    }
                    break;
                case 1: // UPDATE_GAME_MODE
                    player.gameMode.write(buf);
                    break;
                case 2: // UPDATE_LATENCY
                    buf.writeVarInt(player.ping);
                    break;
                case 3: // UPDATE_DISPLAY_NAME
                    if (player.displayName != null) {
                        buf.writeBoolean(true);
                        buf.writeComponent(player.displayName);
                    } else {
                        buf.writeBoolean(false);
                    }
                    break;
            }
        }
    }

    @Override
    public String toString() {
        return "PlayerListItemS2CPacket{" +
                "action=" + action +
                ", players=" + players +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerListItemS2CPacket that = (PlayerListItemS2CPacket) o;

        if (!Objects.equals(action, that.action)) return false;
        return Objects.equals(players, that.players);
    }

    @Override
    public int hashCode() {
        int result = action != null ? action.hashCode() : 0;
        result = 31 * result + (players != null ? players.hashCode() : 0);
        return result;
    }
}
