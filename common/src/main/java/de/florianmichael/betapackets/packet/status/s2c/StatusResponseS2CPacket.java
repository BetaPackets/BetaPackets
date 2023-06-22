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

package de.florianmichael.betapackets.packet.status.s2c;

import com.google.gson.*;
import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.base.Packet;
import de.florianmichael.betapackets.model.ping.PingResponse;
import de.florianmichael.betapackets.model.ping.Player;
import de.florianmichael.betapackets.model.ping.Players;
import de.florianmichael.betapackets.model.ping.Version;

import java.util.Objects;
import java.util.UUID;

public class StatusResponseS2CPacket extends Packet {
    private final static Gson GSON = new GsonBuilder().create();

    public String rawPingResponse;
    public PingResponse pingResponse;

    public StatusResponseS2CPacket(final FunctionalByteBuf buf) {
        rawPingResponse = buf.readString(32767);
        final JsonObject jsonObject = GSON.fromJson(rawPingResponse, JsonObject.class);

        Version version = null;
        if (jsonObject.has("version")) {
            final JsonObject versionObject = jsonObject.get("version").getAsJsonObject();
            version = new Version(versionObject.get("name").getAsString(), versionObject.get("protocol").getAsString());
        }

        Players players = null;
        if (jsonObject.has("players")) {
            final JsonObject playersObject = jsonObject.get("players").getAsJsonObject();
            final int max = playersObject.get("max").getAsInt();
            final int online = playersObject.get("online").getAsInt();

            Player[] sample = null;
            if (playersObject.has("sample")) {
                final JsonArray sampleObject = playersObject.get("sample").getAsJsonArray();
                sample = new Player[sampleObject.size()];

                for (int i = 0; i < sample.length; i++) {
                    final JsonObject playerObject = sampleObject.get(i).getAsJsonObject();
                    sample[i] = new Player(playerObject.get("name").getAsString(), UUID.fromString(playerObject.get("id").getAsString()));
                }
            }

            players = new Players(max, online, sample);
        }

        final String description = jsonObject.get("description").getAsJsonObject().get("text").getAsString();
        final String favicon = jsonObject.has("favicon") ? jsonObject.get("favicon").getAsString() : null;
        final Boolean enforcesSecureChat = jsonObject.has("enforcesSecureChat") ? jsonObject.get("enforcesSecureChat").getAsBoolean() : null;
        final Boolean previewsChat = jsonObject.has("previewsChat") ? jsonObject.get("previewsChat").getAsBoolean() : null;

        pingResponse = new PingResponse(version, players, description, favicon, enforcesSecureChat, previewsChat);
    }

    public StatusResponseS2CPacket(final String rawPingResponse) {
        this.rawPingResponse = rawPingResponse;
    }

    @Override
    public void write(FunctionalByteBuf buf) {
        buf.writeString(rawPingResponse);
    }

    @Override
    public String toString() {
        return "StatusResponseS2CPacket{" +
                "pingResponse=" + pingResponse +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatusResponseS2CPacket that = (StatusResponseS2CPacket) o;

        if (!Objects.equals(rawPingResponse, that.rawPingResponse))
            return false;
        return Objects.equals(pingResponse, that.pingResponse);
    }

    @Override
    public int hashCode() {
        int result = rawPingResponse != null ? rawPingResponse.hashCode() : 0;
        result = 31 * result + (pingResponse != null ? pingResponse.hashCode() : 0);
        return result;
    }
}
