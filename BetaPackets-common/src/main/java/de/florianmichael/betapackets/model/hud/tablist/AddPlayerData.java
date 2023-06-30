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

package de.florianmichael.betapackets.model.hud.tablist;

import de.florianmichael.betapackets.base.ModelMapper;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.auth.GameProfile;
import de.florianmichael.betapackets.model.game.GameMode;
import net.lenni0451.mcstructs.text.ATextComponent;

import java.util.Objects;

public class AddPlayerData {

    public ModelMapper<Integer, GameMode> gameMode = new ModelMapper<>(FunctionalByteBuf::readVarInt, FunctionalByteBuf::writeVarInt, (version, value) -> GameMode.getById(version, value.shortValue()));
    public int ping;
    public ATextComponent displayName;
    public GameProfile gameProfile;

    public AddPlayerData() {
    }

    public AddPlayerData(GameMode gameMode, int ping, ATextComponent displayName, GameProfile gameProfile) {
        this.gameMode = new ModelMapper<>(FunctionalByteBuf::writeVarInt, gameMode);
        this.ping = ping;
        this.displayName = displayName;
        this.gameProfile = gameProfile;
    }

    @Override
    public String toString() {
        return "AddPlayerData{" +
                "gameMode=" + gameMode +
                ", ping=" + ping +
                ", displayName=" + displayName +
                ", gameProfile=" + gameProfile +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddPlayerData that = (AddPlayerData) o;

        if (ping != that.ping) return false;
        if (!Objects.equals(gameMode, that.gameMode)) return false;
        if (!Objects.equals(displayName, that.displayName)) return false;
        return Objects.equals(gameProfile, that.gameProfile);
    }

    @Override
    public int hashCode() {
        int result = gameMode != null ? gameMode.hashCode() : 0;
        result = 31 * result + ping;
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (gameProfile != null ? gameProfile.hashCode() : 0);
        return result;
    }
}
