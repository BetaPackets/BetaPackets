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

package org.betapackets.betapackets.packet.model.s2c.status;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.betapackets.betapackets.netty.bytebuf.FunctionalByteBuf;
import org.betapackets.betapackets.event.PacketEvent;
import org.betapackets.betapackets.model.auth.GameProfile;
import org.betapackets.betapackets.packet.model.PacketWrapper;
import org.betapackets.betapackets.packet.type.Packet;
import org.betapackets.betapackets.packet.type.PacketType;
import net.lenni0451.mcstructs.text.ATextComponent;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class WrapperStatusServerStatusResponse extends PacketWrapper<WrapperStatusServerStatusResponse> {

    private static final Gson GSON = new Gson();

    private String versionName;
    private int versionId;

    private int maxPlayers;
    private int onlinePlayers;
    private List<GameProfile> players;

    private ATextComponent description;
    private Optional<String> favicon;

    private Optional<Boolean> enforcesSecureChat;
    private Optional<Boolean> previewsChat;

    public WrapperStatusServerStatusResponse(PacketEvent event) throws IOException {
        super(event);
    }

    public WrapperStatusServerStatusResponse(String versionName, int versionId, int maxPlayers, int onlinePlayers, List<GameProfile> players, ATextComponent description, Optional<String> favicon, Optional<Boolean> enforcesSecureChat, Optional<Boolean> previewsChat) {
        super(PacketType.Status.Server.STATUS_RESPONSE);
        this.versionName = versionName;
        this.versionId = versionId;
        this.maxPlayers = maxPlayers;
        this.onlinePlayers = onlinePlayers;
        this.players = players;
        this.description = description;
        this.favicon = favicon;
        this.enforcesSecureChat = enforcesSecureChat;
        this.previewsChat = previewsChat;
    }

    @Override
    public void write(Packet type, FunctionalByteBuf buf) {
        JsonObject object = new JsonObject();

        JsonObject versionObject = new JsonObject();
        versionObject.addProperty("name", versionName);
        versionObject.addProperty("protocol", versionId);
        object.add("version", versionObject);

        JsonObject playerObject = new JsonObject();
        playerObject.addProperty("max", maxPlayers);
        playerObject.addProperty("online", onlinePlayers);
        if (!players.isEmpty()) {
            JsonArray sampleArray = new JsonArray();
            players.forEach(profile -> {
                JsonObject profileObject = new JsonObject();
                profileObject.addProperty("name", profile.name);
                profileObject.addProperty("id", profile.uuid.toString());
            });
            playerObject.add("sample", sampleArray);
        }
        object.add("players", playerObject);

        object.add("description", buf.getUserConnection().getTextComponentSerializer().serializeJson(description));

        favicon.ifPresent(str -> object.addProperty("favicon", str));
        enforcesSecureChat.ifPresent(bool -> object.addProperty("enforcesSecureChat", bool));
        previewsChat.ifPresent(bool -> object.addProperty("previewsChat", bool));

        System.out.println(GSON.toJson(object));

        buf.writeString(GSON.toJson(object));
    }

    @Override
    public void read(Packet type, FunctionalByteBuf buf) {
        JsonObject object = GSON.fromJson(buf.readString(), JsonObject.class);

        JsonObject versionObject = object.getAsJsonObject("version");
        versionName = versionObject.get("name").getAsString();
        versionId = versionObject.get("protocol").getAsInt();

        JsonObject playersObject = object.getAsJsonObject("players");
        maxPlayers = playersObject.get("max").getAsInt();
        onlinePlayers = playersObject.get("online").getAsInt();

        players = new LinkedList<>();
        if (playersObject.has("sample")) {
            JsonArray sampleArray = playersObject.getAsJsonArray("sample");
            sampleArray.forEach(profileElement -> {
                JsonObject profileObject = profileElement.getAsJsonObject();
                players.add(new GameProfile(UUID.fromString(profileObject.get("id").getAsString()), profileObject.get("name").getAsString()));
            });
        }

        description = buf.getUserConnection().getTextComponentSerializer()
                .deserialize(object.get("description").getAsJsonObject());

        if (object.has("favicon")) {
            favicon = Optional.of(object.get("favicon").getAsString());
        } else {
            favicon = Optional.empty();
        }

        if (object.has("enforcesSecureChat")) {
            enforcesSecureChat = Optional.of(object.get("enforcesSecureChat").getAsBoolean());
        } else {
            enforcesSecureChat = Optional.empty();
        }

        if (object.has("previewsChat")) {
            previewsChat = Optional.of(object.get("previewsChat").getAsBoolean());
        } else {
            previewsChat = Optional.empty();
        }
    }

    @Override
    public void copyFrom(WrapperStatusServerStatusResponse base) {
        versionName = base.versionName;
        versionId = base.versionId;
        maxPlayers = base.maxPlayers;
        onlinePlayers = base.onlinePlayers;
        players = base.players;
        description = base.description;
        favicon = base.favicon;
        enforcesSecureChat = base.enforcesSecureChat;
        previewsChat = base.previewsChat;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getOnlinePlayers() {
        return onlinePlayers;
    }

    public void setOnlinePlayers(int onlinePlayers) {
        this.onlinePlayers = onlinePlayers;
    }

    public List<GameProfile> getPlayers() {
        return players;
    }

    public void setPlayers(List<GameProfile> players) {
        this.players = players;
    }

    public ATextComponent getDescription() {
        return description;
    }

    public void setDescription(ATextComponent description) {
        this.description = description;
    }

    public Optional<String> getFavicon() {
        return favicon;
    }

    public void setFavicon(Optional<String> favicon) {
        this.favicon = favicon;
    }

    public Optional<Boolean> getEnforcesSecureChat() {
        return enforcesSecureChat;
    }

    public void setEnforcesSecureChat(Optional<Boolean> enforcesSecureChat) {
        this.enforcesSecureChat = enforcesSecureChat;
    }

    public Optional<Boolean> getPreviewsChat() {
        return previewsChat;
    }

    public void setPreviewsChat(Optional<Boolean> previewsChat) {
        this.previewsChat = previewsChat;
    }

    @Override
    public String toString() {
        return "WrapperStatusServerStatusResponse{" +
                "versionName='" + versionName + '\'' +
                ", versionId=" + versionId +
                ", maxPlayers=" + maxPlayers +
                ", onlinePlayers=" + onlinePlayers +
                ", players=" + players +
                ", description=" + description +
                ", favicon='" + favicon + '\'' +
                ", enforcesSecureChat=" + enforcesSecureChat +
                ", previewsChat=" + previewsChat +
                '}';
    }
}
